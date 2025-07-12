package com.example.ticket_um.v1.controller.internal;

import com.example.ticket_um.v1.service.UserService;
import com.example.ticket_utils.v1.domain.dto.UserDtl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("internal/v1/users")
@RestController
public class UserControllerIntl {

  private final UserService userService;

  @GetMapping("{id}")
  public UserDtl getUserDtl(@PathVariable long id) {
    return userService.getUserDtl(id);
  }
}
