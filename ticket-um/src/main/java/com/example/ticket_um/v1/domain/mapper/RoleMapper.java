package com.example.ticket_um.v1.domain.mapper;

import com.example.ticket_um.v1.domain.dto.EditRoleReq;
import com.example.ticket_um.v1.domain.dto.RoleReq;
import com.example.ticket_um.v1.domain.dto.RoleResp;
import com.example.ticket_utils.v1.domain.dto.UserDtl;
import com.example.ticket_um.v1.domain.model.Role;
import com.example.ticket_um.v1.service.GroupService;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Set;

@Mapper(uses = {GroupMapper.class, PermissionMapper.class})
public abstract class RoleMapper {

  @Autowired
  protected GroupService groupService;

  @Mapping(target = "group", expression = "java(groupService.findById(req.getGroupId()))")
  public abstract Role toRole(RoleReq req);

  public abstract void update(EditRoleReq req, @MappingTarget Role role);

  public abstract RoleResp toRoleResp(Role role);

  @IterableMapping(elementTargetType = RoleResp.class)
  public abstract List<RoleResp> toRoleRespList(List<Role> roles);

  @IterableMapping(elementTargetType = RoleResp.class)
  public abstract Set<RoleResp> toRoleRespSet(Set<Role> roles);

  public abstract UserDtl.UserDtlRole toUserDtlRole(Role role);

  public abstract Set<UserDtl.UserDtlRole> toUserDtlRoleSet(Set<Role> roles);
}
