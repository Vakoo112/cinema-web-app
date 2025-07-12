package com.example.ticket_um.v1.domain.mapper;

import com.example.ticket_um.v1.domain.dto.GroupReq;
import com.example.ticket_um.v1.domain.dto.GroupResp;
import com.example.ticket_utils.v1.domain.dto.UserDtl;
import com.example.ticket_um.v1.domain.model.Group;
import org.mapstruct.Mapper;
import java.util.List;
import java.util.Set;

@Mapper
public abstract class GroupMapper {

  public abstract Group toGroup(GroupReq req);

  public abstract GroupResp toGroupResp(Group group);

  public abstract List<GroupResp> toGroupRespList(List<Group> groups);

  public abstract Set<GroupResp> toGroupRespSet(Set<Group> groups);

  public abstract UserDtl.UserDtlGroup toUserDtlGroup(Group group);

  public abstract List<UserDtl.UserDtlGroup> toUserDtlGroupList(List<Group> groups);
}
