package com.example.ticket_um.v1.repository;

import com.example.ticket_um.v1.domain.model.Role;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoleRepo extends JpaRepository<Role, Long> {

  List<Role> findByActive(boolean active, Sort sort);

  List<Role> findByGroupIdIn(List<Long> groupIds, Sort sort);

  List<Role> findByGroupIdInAndActive(List<Long> groupIds, boolean active, Sort sort);
}
