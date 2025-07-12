package notification_service.notification.v1.repository;

import notification_service.notification.v1.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepo extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {

  @Modifying
  @Query("""
      update Notification t
         set t.read = true
       where t.read = false
         and t.userId = :userId""")
  int markAsRead(long userId);

  long countByUserIdAndReadFalse(long userId);
}
