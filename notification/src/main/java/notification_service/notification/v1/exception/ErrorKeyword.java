package notification_service.notification.v1.exception;

import com.example.ticket_utils.v1.exception.Error;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorKeyword implements Error {

  NOTIFICATION_NOT_FOUND("NOTIFICATION NOT FOUND!"),
  NOTIF_TYPE_BY_KEYWORD_NOT_FOUND("NOTIFICATION TYPE BY KEYWORD NOT FOUND!"),
  NOTIF_TYPE_NOT_FOUND("NOTIFICATION TYPE NOT FOUND!"),
  NOTIF_CAN_SEND_ONLY_USER("NOTIFICATION MESSAGES MUST SENT TO USER ONLY!");

  private final String message;
}
