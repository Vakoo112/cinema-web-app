package notification_service.notification.v1.service;

import com.example.ticket_utils.v1.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import notification_service.notification.v1.repository.TypeRepo;
import notification_service.notification.v1.domain.Type;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static notification_service.notification.v1.exception.ErrorKeyword.NOTIF_TYPE_BY_KEYWORD_NOT_FOUND;
import static notification_service.notification.v1.exception.ErrorKeyword.NOTIF_TYPE_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TypeService {

  private final TypeRepo typeRepo;

  public Type findByKeyword(String keyword) {
    return typeRepo.findByKeyword(keyword)
        .orElseThrow(() -> new ResourceNotFoundException(NOTIF_TYPE_BY_KEYWORD_NOT_FOUND));
  }

  public Type findById(long id) {
    return typeRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(NOTIF_TYPE_NOT_FOUND));
  }
}
