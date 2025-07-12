package notification_service.notification.v1.config.security;

import com.example.ticket_utils.v1.config.AbstractAuthFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import notification_service.notification.v1.client.UserManagmentApiClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import static org.springframework.util.StringUtils.hasLength;

@RequiredArgsConstructor
@Component
public class AuthFilter extends AbstractAuthFilter {

  private final UserManagmentApiClient userManagmentApiClient;

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

    var authUser = userManagmentApiClient.getUserDtl(Long.parseLong(userId));
    var authentication = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }
}