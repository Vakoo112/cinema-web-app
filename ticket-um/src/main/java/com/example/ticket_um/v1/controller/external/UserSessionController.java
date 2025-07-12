package com.example.ticket_um.v1.controller.external;

import com.example.ticket_um.v1.domain.dto.AuthReq;
import com.example.ticket_um.v1.domain.dto.AuthResp;
import com.example.ticket_um.v1.service.UserSessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@RequestMapping("external/v1/sessions")
@RestController
public class UserSessionController {

  private final UserSessionService sessionService;

  @PostMapping
  public AuthResp createSession(@RequestBody @Valid AuthReq authReq, HttpServletRequest request) {
    var resp = sessionService.createSession(authReq, getClientIp(request));
    return resp;
  }

  @PostMapping("logout")
  public void logout() {
    sessionService.logout();
  }

  private String getClientIp(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (!hasText(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }
}