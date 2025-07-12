package notification_service.notification.v1.controller.external;

import com.example.ticket_utils.v1.domain.dto.PageReq;
import com.example.ticket_utils.v1.domain.dto.PageResp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notification_service.notification.v1.domain.dto.NotificationResp;
import notification_service.notification.v1.domain.dto.NotificationSearch;
import notification_service.notification.v1.service.NotificationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;

@RequiredArgsConstructor
@RequestMapping("external/v1/notifications")
@RestController
public class NotificationController {

  private final NotificationService notificationService;

  @PutMapping("mark-as-read")
  public void markAsRead(@RequestBody @Valid PageReq<List<Long>> req) {
    notificationService.markAsRead(req.getData());
  }

  @PutMapping("user/{userId}/mark-as-read")
  public void markAsRead(@PathVariable long userId) {
    notificationService.markAsRead(userId);
  }

  @GetMapping("count-unread")
  public Long countUnread() {
    return notificationService.countUnread();
  }

  @PostMapping("search")
  public PageResp<List<NotificationResp>> search(@RequestBody @Valid PageReq<NotificationSearch> req) {
    Sort sort;
    if (!CollectionUtils.isEmpty(req.getSort())) {
      var orders = new ArrayList<Sort.Order>();
      req.getSort().forEach(s -> {
        var order = new Sort.Order(s.getDirection(), s.getProperty());
        orders.add(order);
      });
      sort = Sort.by(orders);
    } else {
      sort = Sort.by(asc("read"), desc("id"));
    }
    var pageable = PageRequest.of(req.getPage().getNumber(), req.getPage().getSize(), sort);
    return notificationService.search(req.getData(), pageable);
  }
}
