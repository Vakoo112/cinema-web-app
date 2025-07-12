package com.example.ticket_um.v1.config.security;

import com.example.ticket_utils.v1.config.AbstractAuthFilter;
import com.example.ticket_um.v1.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasLength;

@RequiredArgsConstructor
@Component
public class AuthFilter extends AbstractAuthFilter {

  private final UserService userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {
    final String userId = request.getHeader("X-User-ID");
    if (!hasLength(userId)) {
      chain.doFilter(request, response);
      return;
    }

    var authUser = userService.getUserDtl(Long.parseLong(userId));
    var authentication = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }
}
