package com.example.ticket_utils.v1.config;

import com.example.ticket_utils.v1.domain.dto.UserDtl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class AuthorizationUtil {

  private AuthorizationUtil() {
  }

  public static UserDtl getAuthUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDtl user) {
      return user;
    }
    return null;
  }
}

