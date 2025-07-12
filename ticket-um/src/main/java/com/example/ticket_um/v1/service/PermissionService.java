package com.example.ticket_um.v1.service;

import com.example.ticket_um.v1.domain.dto.PermResp;
import com.example.ticket_um.v1.domain.mapper.PermissionMapper;
import com.example.ticket_um.v1.domain.model.Permission;
import com.example.ticket_um.v1.repository.PermRepo;
import com.example.ticket_utils.v1.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.example.ticket_um.v1.exception.ErrorKeyword.PERM_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PermissionService {

  private final PermRepo permRepo;
  private final PermissionMapper permMapper;

  public PermResp get(long id) {
    var perm = findById(id);
    return permMapper.toPermResp(perm);
  }

  public List<PermResp> getAll(Boolean active) {
    List<Permission> perms;
    if (active == null) {
      perms = permRepo.findAll(Sort.by("type", "id"));
    } else {
      perms = permRepo.findByActive(active, Sort.by("type", "id"));
    }
    return permMapper.toPermRespList(perms);
  }

  public List<PermResp> getByType(long typeId, Boolean active) {
    List<Permission> perms;
    if (active == null) {
      perms = permRepo.findByTypeId(typeId, Sort.by("type", "id"));
    } else {
      perms = permRepo.findByTypeIdAndActive(typeId, active, Sort.by( "type", "id"));
    }
    return permMapper.toPermRespList(perms);
  }

  public Permission findById(long id) {
    return permRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(PERM_NOT_FOUND));
  }
}
