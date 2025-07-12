package com.example.ticket_um.v1.domain.mapper;

import com.example.ticket_um.v1.domain.dto.PermResp;
import com.example.ticket_utils.v1.domain.dto.UserDtl;
import com.example.ticket_um.v1.domain.model.Permission;
import org.mapstruct.Mapper;
import java.util.List;
import java.util.Set;

@Mapper(uses = DictionaryMapper.class)
public abstract class PermissionMapper {

  public abstract PermResp toPermResp(Permission perm);

  public abstract List<PermResp> toPermRespList(List<Permission> perms);

  public abstract Set<PermResp> toPermRespSet(Set<Permission> perms);

  public abstract UserDtl.UserDtlPermission toUserDtlPermission(Permission perm);

  public abstract Set<UserDtl.UserDtlPermission> toUserDtlPermissionSet(Set<Permission> perms);
}
