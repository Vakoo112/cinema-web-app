package com.example.ticket_um.v1.service;

import com.example.ticket_um.v1.config.security.JwtUtil;
import com.example.ticket_um.v1.domain.dto.AuthReq;
import com.example.ticket_um.v1.domain.dto.AuthResp;
import com.example.ticket_um.v1.domain.mapper.UserMapper;
import com.example.ticket_um.v1.domain.model.UserSession;
import com.example.ticket_um.v1.domain.model.Users;
import com.example.ticket_um.v1.repository.UserSessionRepo;
import com.example.ticket_utils.v1.exception.UnauthorizedException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import static com.example.ticket_utils.v1.config.AuthorizationUtil.getAuthUser;
import static com.example.ticket_um.v1.domain.model.enums.TokenType.PWD_CHNG;
import static com.example.ticket_um.v1.domain.model.enums.TokenType.SESSION;
import static com.example.ticket_um.v1.exception.ErrorKeyword.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserSessionService {

  private final AuthenticationManager authenticationManager;
  private final UserSessionRepo sessionRepo;
  private final UserMapper userMapper;
  private final JwtUtil jwtUtil;
  private final EntityManager entityManager;
  private final UserService userService;

  @Transactional
  private Users authenticate(String username, String password) {
    Authentication authentication = null;
    try {
      authentication = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (BadCredentialsException ex) {
      int attempts = userService.authFailAttempt(username);
      if (attempts == 5) {
        throw new UnauthorizedException(USER_LOCKED, USER_LOCKED.getMessage() + " 10 MINUTE!");
      }
      if (attempts == 10) {
        throw new UnauthorizedException(USER_LOCKED, USER_LOCKED.getMessage() + " 1 HOUR!");
      }
      throw ex;
    } catch (LockedException ex) {
      var user = userService.getByUsername(username);
      if (user.getAuthFailAttempts() == 5) {
        throw new UnauthorizedException(USER_LOCKED, USER_LOCKED.getMessage() + " 10 MINUTE!");
      }
      if (user.getAuthFailAttempts() == 10) {
        throw new UnauthorizedException(USER_LOCKED, USER_LOCKED.getMessage() + " 1 HOUR!");
      }
      throw ex;
    }

    var user = (Users) authentication.getPrincipal();

    //clear failed attepts
    userService.clearAuthFailAttempt(user.getId());

    return user;
  }

  @Transactional
  public AuthResp createSession(AuthReq req, String ipAddress) {
    var user = authenticate(req.getUsername().toUpperCase(), req.getPassword());

    var tokenType = SESSION;

    if (user.getPwdNextChngDate().isBefore(LocalDateTime.now())) {
      tokenType = PWD_CHNG;
    }

    var token = jwtUtil.generateJwt(user.getId(), tokenType);
    var authResp = new AuthResp(token, tokenType);

    if (tokenType == SESSION) {
      addTokenSession(user, ipAddress, token, authResp);
    }

    return authResp;
  }

  @Transactional
  private void addTokenSession(Users user, String ipAddress, String token, AuthResp authResp) {
    //find old active sessions and logout
    sessionRepo.findByUser_Id(user.getId()).ifPresent(s -> {
      logout(s);
      entityManager.flush();
    });

    var session = new UserSession(user, ipAddress, token);
    sessionRepo.save(session);
    authResp.setUser(userMapper.toUserResp(user));
    authResp.setLastSession(sessionRepo.getLastSession(user.getId()).orElse(null));
  }

  @Transactional
  public void checkTokenAndUpdateLastActPath(String token, String actPath) {
    var session = getLockedByToken(token);
    if (session.getLastActDate().plusHours(1).isBefore(LocalDateTime.now())) {
      throw new UnauthorizedException(SESSION_EXPIRED);
    }
    session.setLastActPath(actPath);
    session.setLastActDate(LocalDateTime.now());
  }

  @Transactional
  public void logout() {
    var authUserId = getAuthUser().getId();
    var session = findByUserId(authUserId);
    logout(session);
  }

  @Transactional
  private void logout(UserSession session) {
    sessionRepo.delete(session);
  }

  @Transactional
  private UserSession getLockedByToken(String token) {
    return sessionRepo.getLockedByToken(token)
        .orElseThrow(() -> new UnauthorizedException(SESSION_NOT_FOUND));
  }

  private UserSession findByUserId(long userId) {
    return sessionRepo.findByUser_Id(userId)
        .orElseThrow(() -> new UnauthorizedException(SESSION_NOT_FOUND));
  }
}
