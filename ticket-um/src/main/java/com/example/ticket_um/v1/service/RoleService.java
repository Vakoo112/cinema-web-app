package com.example.ticket_um.v1.service;

import com.example.ticket_um.v1.domain.dto.EditRoleReq;
import com.example.ticket_um.v1.domain.dto.PermResp;
import com.example.ticket_um.v1.domain.dto.RoleReq;
import com.example.ticket_um.v1.domain.dto.RoleResp;
import com.example.ticket_um.v1.domain.mapper.PermissionMapper;
import com.example.ticket_um.v1.domain.mapper.RoleMapper;
import com.example.ticket_um.v1.domain.model.Role;
import com.example.ticket_um.v1.repository.RoleRepo;
import com.example.ticket_utils.v1.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.example.ticket_um.v1.exception.ErrorKeyword.ROLE_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class RoleService {

  private final RoleRepo roleRepo;
  private final RoleMapper roleMapper;
  private final PermissionService permService;
  private final PermissionMapper permMapper;
  private final UserService userService;

  @Transactional
  public RoleResp add(RoleReq req) {
    var role = roleMapper.toRole(req);
    roleRepo.save(role);
    return roleMapper.toRoleResp(role);
  }

  @Transactional
  public RoleResp edit(long id, EditRoleReq req) {
    var role = findById(id);
    roleMapper.update(req, role);
    return roleMapper.toRoleResp(role);
  }

  @Transactional
  public RoleResp chngState(long id, boolean active) {
    var role = findById(id);
    role.setActive(active);

    if (!active) {
      userService.delUserRoles(id);
    }

    return roleMapper.toRoleResp(role);
  }

  @Transactional
  public RoleResp addRolePerm(long id, long permId) {
    var role = findById(id);
    var perm = permService.findById(permId);
    role.addPerm(perm);
    return roleMapper.toRoleResp(role);
  }

  @Transactional
  public RoleResp delRolePerm(long id, long permId) {
    var role = findById(id);
    var perm = permService.findById(permId);
    role.delPerm(perm);
    return roleMapper.toRoleResp(role);
  }

  public RoleResp get(long id) {
    var role = findById(id);
    return roleMapper.toRoleResp(role);
  }

  public List<RoleResp> getAll(Boolean active) {
    List<Role> roles;
    if (active == null) {
      roles = roleRepo.findAll(Sort.by("id"));
    } else {
      roles = roleRepo.findByActive(active, Sort.by( "id"));
    }
    return roleMapper.toRoleRespList(roles);
  }

  public List<RoleResp> getByGroup(long groupId, Boolean active) {
    return roleMapper.toRoleRespList(findByGroup(List.of(groupId), active));
  }

  List<Role> findByGroup(List<Long> groupIds, Boolean active) {
    if (active == null) {
      return roleRepo.findByGroupIdIn(groupIds, Sort.by("id"));
    } else {
      return roleRepo.findByGroupIdInAndActive(groupIds, active, Sort.by( "id"));
    }
  }

  public RoleResp getRolePerms(long id) {
    var role = findById(id);
    return roleMapper.toRoleResp(role);
  }

  public List<PermResp> getPerms(List<Long> ids) {
    var roles = findAllById(ids);
    var perms = roles.stream().flatMap(t -> t.getPermissions().stream()).distinct().toList();
    return permMapper.toPermRespList(perms);
  }

  public List<Role> findAllById(List<Long> ids) {
    return roleRepo.findAllById(ids);
  }

  public Role findById(long id) {
    return roleRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(ROLE_NOT_FOUND));
  }
}
