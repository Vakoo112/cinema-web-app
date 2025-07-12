package com.example.ticket_um.v1.repository;

import com.example.ticket_um.v1.domain.model.Permission;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PermRepo extends  JpaRepository<Permission,Long> {

  List<Permission> findByActive(boolean active, Sort sort);

  List<Permission> findByTypeIdAndActive(long typeId, boolean active, Sort sort);

  List<Permission> findByTypeId(long typeId, Sort sort);
}
