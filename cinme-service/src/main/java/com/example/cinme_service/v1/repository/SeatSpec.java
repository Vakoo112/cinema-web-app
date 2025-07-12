package com.example.cinme_service.v1.repository;

import com.example.cinme_service.v1.domain.Seat;
import com.example.cinme_service.v1.domain.dto.SeatSearch;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import static org.springframework.util.StringUtils.hasText;

@Data
@RequiredArgsConstructor
public class SeatSpec implements Specification<Seat> {

  private final SeatSearch seatSearchQuery;

  @Override
  public Predicate toPredicate(Root<Seat> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    var predicates = new ArrayList<Predicate>();

    predicates.add(criteriaBuilder.equal(root.get("deleted"), false));

    if (seatSearchQuery.getSeatId() != null) {
      predicates.add(criteriaBuilder.equal(root.get("id"), seatSearchQuery.getSeatId()));
    }

    if (seatSearchQuery.getHallId() != null) {
      predicates.add(criteriaBuilder.equal(root.get("hall").get("id"), seatSearchQuery.getHallId()));
    }

    if (hasText(seatSearchQuery.getSeatNumber())) {
      predicates.add(criteriaBuilder.equal(root.get("seatNumber"), seatSearchQuery.getSeatNumber()));
    }

    if (hasText(seatSearchQuery.getColorCode())) {
      predicates.add(criteriaBuilder.equal(root.get("colorCode"), seatSearchQuery.getColorCode()));
    }

    if (seatSearchQuery.getSeatCondition() != null) {
      predicates.add(criteriaBuilder.equal(root.get("seatCondition"), seatSearchQuery.getSeatCondition()));
    }

    return predicates.stream().reduce(criteriaBuilder::and).orElse(criteriaBuilder.and());
  }
}
