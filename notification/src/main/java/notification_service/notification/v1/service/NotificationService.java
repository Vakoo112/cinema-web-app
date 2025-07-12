package notification_service.notification.v1.service;

import com.example.ticket_utils.v1.domain.dto.PageResp;
import com.example.ticket_utils.v1.exception.ForbiddenException;
import com.example.ticket_utils.v1.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import notification_service.notification.v1.domain.NotificationMapper;
import notification_service.notification.v1.domain.dto.NotificationResp;
import notification_service.notification.v1.domain.dto.NotificationSearch;
import notification_service.notification.v1.kafka.NotificationData;
import notification_service.notification.v1.repository.NotifSpec;
import notification_service.notification.v1.repository.NotificationRepo;
import notification_service.notification.v1.client.UserManagmentApiClient;
import notification_service.notification.v1.domain.Notification;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import static com.example.ticket_utils.v1.config.AuthorizationUtil.getAuthUser;
import static notification_service.notification.v1.exception.ErrorKeyword.NOTIFICATION_NOT_FOUND;
import static notification_service.notification.v1.exception.ErrorKeyword.NOTIF_CAN_SEND_ONLY_USER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationRepo notificationRepo;
  private final TypeService typeService;
  private final UserManagmentApiClient userManagmentApiClient;
  private final NotificationMapper notificationMapper;

  @Transactional
  public void add(NotificationData req) {
    var notifType = typeService.findByKeyword(req.getNotifTypeKeyword());
    var notifText = genTemplate(notifType.getTemplate(), req.getParameters());

    if (userManagmentApiClient.getUserDtl(req.getUserId()).hasGroup("USER")) {
      throw new ForbiddenException(NOTIF_CAN_SEND_ONLY_USER);
    }

    var notif = new Notification();
    notif.setMovieShowId(req.getMovieShowId());
    notif.setUniqueCode(req.getUniqueCode());
    notif.setUserId(req.getUserId());
    notif.setNotifType(notifType);
    notif.setNotifText(notifText);

    notificationRepo.save(notif);
  }

  public String genTemplate(String template, Map<String, String> parameters) {
    var substitutor = new StringSubstitutor(parameters);
    return substitutor.replace(template);
  }

  @Transactional
  public void markAsRead(List<Long> ids) {
    notificationRepo.findAllById(ids).stream().forEach(t -> t.setRead(true));
  }

  @Transactional
  public void markAsRead(long userId) {
    notificationRepo.markAsRead(userId);
  }

  public long countUnread() {
    return notificationRepo.countByUserIdAndReadFalse(getAuthUser().getId());
  }

  public PageResp<List<NotificationResp>> search(NotificationSearch req, Pageable pageable) {
    var spec = new NotifSpec(req);
    var notifPage = notificationRepo.findAll(spec, pageable);
    var notifRespList = notificationMapper.toNotifRespList(notifPage.getContent());
    var pageResp = new PageResp.PageSizeResp(
        notifPage.getTotalElements(),
        notifPage.getTotalPages(),
        notifPage.getSize(),
        notifPage.getNumber());
    return new PageResp<>(notifRespList, pageResp);
  }

  public Notification findById(long id) {
    return notificationRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(NOTIFICATION_NOT_FOUND));
  }
}
