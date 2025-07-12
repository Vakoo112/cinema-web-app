package notification_service.notification.v1.domain;

import notification_service.notification.v1.domain.dto.NotificationResp;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper
public abstract class NotificationMapper {

  public abstract NotificationResp toNotificationResp(Notification notif);

  public abstract List<NotificationResp> toNotifRespList(List<Notification> notifs);
}
