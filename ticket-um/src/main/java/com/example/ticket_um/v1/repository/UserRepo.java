package com.example.ticket_um.v1.repository;

import com.example.ticket_um.v1.domain.model.Users;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<Users,Long>, JpaSpecificationExecutor<Users> {

  Optional<Users> findByUsername(String username);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Users> findLockedByUsername(String username);

  boolean existsByUsername(String userName);

  @Modifying
  @Query(value = """
                 delete from user_roles ur
                  where ur.um_role_id in :roleIds""", nativeQuery = true)
  void delUserRoles(List<Long> roleIds);
}
