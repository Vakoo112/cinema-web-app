package com.example.ticket_um.v1.service;

import com.example.ticket_um.v1.domain.dto.GroupReq;
import com.example.ticket_um.v1.domain.dto.GroupResp;
import com.example.ticket_um.v1.domain.mapper.GroupMapper;
import com.example.ticket_um.v1.domain.model.Group;
import com.example.ticket_um.v1.repository.GroupRepo;
import com.example.ticket_utils.v1.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.example.ticket_um.v1.exception.ErrorKeyword.GROUP_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupService {

  private final GroupRepo groupRepo;
  private final GroupMapper groupMapper;

  @Transactional
  public GroupResp add(GroupReq req) {
    var group = groupMapper.toGroup(req);
    groupRepo.save(group);
    return groupMapper.toGroupResp(group);
  }

  public GroupResp get(long id) {
    var group = findById(id);
    return groupMapper.toGroupResp(group);
  }

  public List<GroupResp> getAll() {
    var groups = groupRepo.findAll(Sort.by("id"));
    return groupMapper.toGroupRespList(groups);
  }

  public List<Group> findAllById(List<Long> ids) {
    return groupRepo.findAllById(ids);
  }

  public Group findById(long id) {
    return groupRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(GROUP_NOT_FOUND));
  }
}
