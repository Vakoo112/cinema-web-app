package com.example.ticket_um.v1.repository;

import com.example.ticket_um.v1.domain.dto.SessionResp;
import com.example.ticket_um.v1.domain.model.UserSession;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserSessionRepo extends JpaRepository<UserSession, Long> {

  Optional<UserSession> findByUser_Id(long userId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<UserSession> getLockedByToken(String token);

  @Query(value = """
                 select t.id,
                        t.create_date as createdDate,
                        t.ip_address as ipAddress
                   from m1_user_sessions_log t
                  where t.user_id = :userId
                    and t.token is not null
                  order by t.id desc
                  fetch first 1 row only""", nativeQuery = true)
  Optional<SessionResp> getLastSession(long userId);
}
