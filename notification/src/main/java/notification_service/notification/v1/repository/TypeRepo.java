package notification_service.notification.v1.repository;

import notification_service.notification.v1.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TypeRepo extends JpaRepository<Type,Long> {

  Optional<Type> findByKeyword(String keyword);

}
