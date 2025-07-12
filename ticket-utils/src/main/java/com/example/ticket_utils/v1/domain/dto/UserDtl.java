package com.example.ticket_utils.v1.domain.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

@Data
public class UserDtl implements UserDetails {

  private Long id;
  private Boolean active;
  private String username;
  private String firstName;
  private String lastName;
  private String idnumber;
  private List<UserDtlGroup> groups = new ArrayList<>();
  private Set<UserDtlRole> roles = new HashSet<>();
  private Set<UserDtlPermission> authorities = new HashSet<>();

  public boolean hasGroup(String keyword) {
    return groups.stream().anyMatch(g -> g.getKeyword().equals(keyword));
  }

  public boolean hasPermission(String keyword) {
    return authorities.stream().anyMatch(p -> p.getKeyword().equals(keyword));
  }

  @Override
  public Collection<UserDtlPermission> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return "";
  }

  @Override
  public boolean isAccountNonExpired() {
    return getActive();
  }

  @Override
  public boolean isAccountNonLocked() {
    return getActive();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return getActive();
  }

  @Override
  public boolean isEnabled() {
    return getActive();
  }

  @Data
  public static class UserDtlGroup {

    private Long id;
    private String name;
    private String description;
    private String keyword;
  }

  @Data
  public static class UserDtlRole {

    private Long id;
    private String name;
    private String description;
  }

  @Data
  public static class UserDtlPermission implements GrantedAuthority {
    private Long id;
    private String keyword;
    private String name;
    private String description;

    @Override
    public String getAuthority() {
      return keyword;
    }
  }
}