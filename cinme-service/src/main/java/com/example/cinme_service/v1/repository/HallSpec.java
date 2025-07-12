package com.example.cinme_service.v1.repository;

import com.example.cinme_service.v1.domain.Hall;
import com.example.cinme_service.v1.domain.dto.HallSearch;
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
public class HallSpec implements Specification<Hall> {

  private final HallSearch hallSearchQuery;

  @Override
  public Predicate toPredicate(Root<Hall> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    var predicates = new ArrayList<Predicate>();

    predicates.add(criteriaBuilder.equal(root.get("deleted"), false));

    if (hallSearchQuery.getHallId() != null) {
      predicates.add(criteriaBuilder.equal(root.get("id"), hallSearchQuery.getHallId()));
    }

    if (hasText(hallSearchQuery.getName())) {
      predicates.add(criteriaBuilder.like(root.get("name"), hallSearchQuery.getName()));
    }

    if (hasText(hallSearchQuery.getType())) {
      predicates.add(criteriaBuilder.like(root.get("type"), hallSearchQuery.getType()));
    }

    if (hasText(hallSearchQuery.getLocation())) {
      predicates.add(criteriaBuilder.like(root.get("location"), hallSearchQuery.getName()));
    }

    if (hallSearchQuery.getCapacityFrom() != null) {
      predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("capacity"), hallSearchQuery.getCapacityFrom()));
    }

    if (hallSearchQuery.getCapacityTo() != null) {
      predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("capacity"), hallSearchQuery.getCapacityTo()));
    }

    return predicates.stream().reduce(criteriaBuilder::and).orElse(criteriaBuilder.and());
  }
}
