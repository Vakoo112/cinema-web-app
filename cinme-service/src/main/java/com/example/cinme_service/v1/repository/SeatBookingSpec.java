package com.example.cinme_service.v1.repository;

import com.example.cinme_service.v1.domain.SeatBooking;
import com.example.cinme_service.v1.domain.dto.SeatBookingSearch;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalTime;
import java.util.ArrayList;

@Data
@RequiredArgsConstructor
public class SeatBookingSpec implements Specification<SeatBooking> {

  private final SeatBookingSearch seatBookingSearchQuery;

  @Override
  public Predicate toPredicate(Root<SeatBooking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    var predicates = new ArrayList<Predicate>();

    if (seatBookingSearchQuery.getMovieShowId() != null) {
      predicates.add(criteriaBuilder.equal(root.get("movieShow").get("id"), seatBookingSearchQuery.getMovieShowId()));
    }

    if (seatBookingSearchQuery.getBookedFrom() != null) {
      predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bookedAt"), seatBookingSearchQuery.getBookedFrom().atStartOfDay()));
    }

    if (seatBookingSearchQuery.getBookedTo() != null) {
      predicates.add(criteriaBuilder.lessThan(root.get("bookedAt"), seatBookingSearchQuery.getBookedTo().atTime(LocalTime.MAX)));
    }

    return predicates.stream().reduce(criteriaBuilder::and).orElse(criteriaBuilder.and());
  }
}
