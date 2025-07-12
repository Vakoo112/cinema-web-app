package com.example.ticket_um.v1.controller.internal;

import com.example.ticket_um.v1.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("internal/v1/sessions")
@RestController
public class SessionControllerIntlernal {

  private final UserSessionService sessionService;

  @PutMapping("token/{token}")
  public void checkAndUpdateToken(@PathVariable String token, String actPath) {
    sessionService.checkTokenAndUpdateLastActPath(token, actPath);
  }
}
