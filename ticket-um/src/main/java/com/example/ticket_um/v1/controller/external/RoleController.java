package com.example.ticket_um.v1.controller.external;

import com.example.ticket_um.v1.domain.dto.EditRoleReq;
import com.example.ticket_um.v1.domain.dto.PermResp;
import com.example.ticket_um.v1.domain.dto.RoleReq;
import com.example.ticket_um.v1.domain.dto.RoleResp;
import com.example.ticket_um.v1.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("external/v1/roles")
@RestController
public class RoleController {

  private final RoleService roleService;

  @PostMapping
  public RoleResp add(@RequestBody @Valid RoleReq req) {
    var resp = roleService.add(req);
    return resp;
  }

  @PutMapping("{id}")
  public RoleResp edit(@PathVariable long id, @RequestBody @Valid EditRoleReq req) {
    var resp = roleService.edit(id, req);
    return resp;
  }

  @PatchMapping("{id}/state/{active}")
  public RoleResp chngState(@PathVariable long id, @PathVariable boolean active) {
    var resp = roleService.chngState(id, active);
    return resp;
  }

  @PostMapping("{id}/perm/{permId}")
  public RoleResp addRolePerm(@PathVariable long id, @PathVariable long permId) {
    var resp = roleService.addRolePerm(id, permId);
    return resp;
  }

  @DeleteMapping("{id}/perm/{permId}")
  public RoleResp delRolePerm(@PathVariable long id, @PathVariable long permId) {
    var resp = roleService.delRolePerm(id, permId);
    return resp;
  }

  @GetMapping("{id}")
  public RoleResp get(@PathVariable long id) {
    var resp = roleService.get(id);
    return resp;
  }

  @GetMapping(value = {"", "active/{active}"})
  public List<RoleResp> getAll(@PathVariable(required = false) Boolean active) {
    var resp = roleService.getAll(active);
    return resp;
  }

  @GetMapping(value = {"group/{groupId}", "group/{groupId}/active/{active}"})
  public List<RoleResp> getByGroup(@PathVariable long groupId, @PathVariable(required = false) Boolean active) {
    var resp = roleService.getByGroup(groupId, active);
    return resp;
  }

  @GetMapping("{id}/permission")
  public RoleResp getRolePerms(@PathVariable long id) {
    var resp = roleService.getRolePerms(id);
    return resp;
  }

  @GetMapping("permission")
  public List<PermResp> getPerms(@RequestBody List<Long> idsReq) {
    var resp = roleService.getPerms(idsReq);
    return resp;
  }
}
