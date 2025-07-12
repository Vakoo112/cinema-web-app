package com.example.ticket_um.v1.controller.external;

import com.example.ticket_um.v1.domain.dto.*;
import com.example.ticket_um.v1.service.UserService;
import com.example.ticket_utils.v1.domain.dto.PageReq;
import com.example.ticket_utils.v1.domain.dto.PageResp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("external/v1/users")
public class UsersController {

  @Autowired
  private UserService userService;

  @PostMapping
  public UserResp add(@RequestBody @Valid UserReq req) {
    var resp = userService.add(req);
    return  resp;
  }

  @PreAuthorize("hasAuthority('EDIT_USER')")
  @PutMapping("{id}")
  public UserResp edit(@PathVariable long id, @RequestBody @Valid EditUserReq req) {
    var resp = userService.edit(id, req);
    return resp;
  }

  @PreAuthorize("hasAuthority('CHANGE_STATE')")
  @PatchMapping("{id}/state/{active}")
  public UserResp chngState(@PathVariable long id, @PathVariable boolean active) {
    var resp = userService.chngState(id, active);
    return resp;
  }

  @PreAuthorize("hasAuthority('ADD_ROLE')")
  @PostMapping("{id}/role/{roleId}")
  public UserResp addRole(@PathVariable long id, @PathVariable long roleId) {
    var resp = userService.addRole(id, roleId);
    return resp;
  }

  @PreAuthorize("hasAuthority('ADD_USER')")
  @DeleteMapping("{id}/role/{roleId}")
  public UserResp delRole(@PathVariable long id, @PathVariable long roleId) {
    var resp = userService.delRole(id, roleId);
    return resp;
  }

  @PutMapping("password")
  public void chngPassword(@RequestBody @Valid PwdChangeReq req) {
    userService.chngPassword(req);
  }

  @PreAuthorize("hasAuthority('RESET_PASSWORD')")
  @PutMapping("{id}/reset-password")
  public void resetPassword(@PathVariable long id) {
    userService.resetPassword(id);
  }

  @PreAuthorize("hasAuthority('SEARCH_USERS')")
  @GetMapping("{id}")
  public UserResp get(@PathVariable("id") Long id) {
    return userService.get(id);
  }

  @PreAuthorize("hasAuthority('SEARCH_USERS')")
  @PostMapping("search")
  public PageResp<List<UserResp>> search(@RequestBody @Valid PageReq<UserSearchReq> req){
    Sort sort;
    if(!CollectionUtils.isEmpty(req.getSort())){
      var orders = new ArrayList<Sort.Order>();
      req.getSort().forEach(s -> {
        var order = new Sort.Order(s.getDirection(), s.getProperty());
        orders.add(order);
      });
      sort = Sort.by(orders);
    } else {
      sort = Sort.by("id");
    }
    var pageable = PageRequest.of(req.getPage().getNumber(), req.getPage().getSize(), sort);
    return userService.search(req.getData(),pageable);
  }
}
