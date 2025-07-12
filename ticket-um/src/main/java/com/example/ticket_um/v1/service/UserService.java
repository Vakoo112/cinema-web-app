package com.example.ticket_um.v1.service;

import com.example.ticket_utils.v1.exception.RequestProblemException;
import com.example.ticket_utils.v1.exception.ResourceNotFoundException;
import com.example.ticket_utils.v1.domain.dto.PageResp;
import com.example.ticket_um.v1.domain.dto.*;
import com.example.ticket_um.v1.domain.mapper.UserMapper;
import com.example.ticket_um.v1.domain.model.Users;
import com.example.ticket_um.v1.repository.UserRepo;
import com.example.ticket_um.v1.repository.UserSpec;
import com.example.ticket_utils.v1.domain.dto.UserDtl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static com.example.ticket_utils.v1.config.AuthorizationUtil.getAuthUser;
import static com.example.ticket_um.v1.exception.ErrorKeyword.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepo userRepo;
  private final UserMapper userMapper;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public UserResp add(UserReq req) {
    if (userRepo.existsByUsername(req.getUsername())) {
      throw new ResourceNotFoundException(USER_ALREADY_EXISTS);
    }

    var user = userMapper.toUser(req);
    chngPassword(user, "temp", true);
    userRepo.saveAndFlush(user);
    return userMapper.toUserResp(user);
  }

  @Transactional
  public UserResp edit(long id, EditUserReq req) {
    var user = findById(id);
    userMapper.update(req, user);
    userRepo.flush();
    return userMapper.toUserResp(user);
  }

  @Transactional
  public UserResp chngState(long id, boolean active) {
    var user = findById(id);
    user.setActive(active);
    return userMapper.toUserResp(user);
  }

  @Transactional
  public UserResp addRole(long id, long roleId) {
    var user = findById(id);
    var role = roleService.findById(roleId);
    //TODO logic
    user.addRole(role);
    return userMapper.toUserResp(user);
  }

  @Transactional
  public UserResp delRole(long id, long roleId) {
    var user = findById(id);
    var role = roleService.findById(roleId);
    user.delRole(role);
    return userMapper.toUserResp(user);
  }

  @Transactional
  void delUserRoles(long roleId) {
    userRepo.delUserRoles(List.of(roleId));
  }

  public UserResp get(Long id) {
    Users user = findById(id);
    return userMapper.toUserResp(user);
  }

  public UserDtl getUserDtl(long id) {
    var user = findById(id);
    return userMapper.touUserDtl(user);
  }

  public PageResp<List<UserResp>> search(UserSearchReq req, Pageable pageable) {
    var spec = new UserSpec(req);
    var userPage = userRepo.findAll(spec, pageable);
    var userRespList = userMapper.toUserRespList(userPage.getContent());
    var pageResp = new PageResp.PageSizeResp(
        userPage.getTotalElements(),
        userPage.getTotalPages(),
        userPage.getSize(),
        userPage.getNumber());
    return new PageResp<>(userRespList, pageResp);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepo.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(String.format("User - %s, not found", username)));
  }

  Users getByUsername(String username) {
    return userRepo.findByUsername(username).orElse(null);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  int authFailAttempt(String username) {
    AtomicInteger attempts = new AtomicInteger(0);
    userRepo.findLockedByUsername(username).ifPresent(u -> {
      attempts.addAndGet(u.incAuthFailAttempts());
      if (attempts.get() == 5) {
        u.setLockDate(LocalDateTime.now());
        u.setUnlockDate(LocalDateTime.now().plusMinutes(10));
      } else if (attempts.get() == 10) {
        u.setLockDate(LocalDateTime.now());
        u.setUnlockDate(LocalDateTime.now().plusHours(1));
      }
    });
    return attempts.get();
  }

  @Transactional
  void clearAuthFailAttempt(long id) {
    var user = findById(id);
    user.setAuthFailAttempts(0);
    user.setLockDate(null);
    user.setUnlockDate(null);
  }

  @Transactional
  public void chngPassword(PwdChangeReq req) {
    var authUserDtl = getAuthUser();
    var authUser = findById(authUserDtl.getId());

    if (!passwordEncoder.matches(req.getCrntPwd(), authUser.getPassword())) {
      throw new RequestProblemException(INCORRECT_PWD);
    }

    if (req.getCrntPwd().equals(req.getNewPwd())) {
      throw new RequestProblemException(OLD_AND_NEW_PWDS_MATCH);
    }

    if (!req.getNewPwd().equals(req.getRetypeNewPwd())) {
      throw new RequestProblemException(NEW_PWDS_NOT_MATCH);
    }

    chngPassword(authUser, req.getNewPwd(), false);
  }


  @Transactional
  private void chngPassword(Users user, String newPwd, boolean reset) {
    user.setPassword(passwordEncoder.encode(newPwd));
    user.setPwdChngDate(LocalDateTime.now());
    var nexstChngDate = LocalDateTime.now();
    if (!reset) {
      nexstChngDate = nexstChngDate.plusMonths(3);
    }
    user.setPwdNextChngDate(nexstChngDate);
  }

  @Transactional
  public void resetPassword(long id) {
    var user = findById(id);
    chngPassword(user, "temp", true);
  }

  public Users findById(long id) {
    return userRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
  }
}
