package notification_service.notification.v1.domain.dto;

import com.example.ticket_utils.v1.domain.dto.DcResp;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationResp {

    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long userId;
    private boolean read;
    private Long movieShowId;
    private String uniqueCode;
    private DcResp notifType;
    private String notifText;
}
