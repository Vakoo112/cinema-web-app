package notification_service.notification.v1.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import notification_service.notification.v1.domain.Notification;
import notification_service.notification.v1.domain.dto.NotificationSearch;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import static com.example.ticket_utils.v1.config.AuthorizationUtil.getAuthUser;

@Data
@RequiredArgsConstructor
public class NotifSpec implements Specification<Notification> {

  private final NotificationSearch searchQuery;


  @Override
  public Predicate toPredicate(Root<Notification> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    var predicates = new ArrayList<Predicate>();
    predicates.add(criteriaBuilder.equal(root.get("userId"), getAuthUser().getId()));

    if (searchQuery.getCreatedDateFrom() != null) {
      predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), searchQuery.getCreatedDateFrom()));
    }

    if (searchQuery.getCreatedDateTo() != null) {
      predicates.add(criteriaBuilder.lessThan(root.get("createdDate"), searchQuery.getCreatedDateTo().plusDays(1)));
    }

    if (searchQuery.getReadDateFrom() != null) {
      predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("modifiedDate"), searchQuery.getReadDateFrom()));
    }

    if (searchQuery.getReadDateTo() != null) {
      predicates.add(criteriaBuilder.lessThan(root.get("modifiedDate"), searchQuery.getReadDateTo().plusDays(1)));
    }

    return predicates.stream().reduce(criteriaBuilder::and).orElse(criteriaBuilder.and());
  }
}
