package com.example.cinme_service.v1.repository;

import com.example.cinme_service.v1.domain.Movie;
import com.example.cinme_service.v1.domain.dto.MovieSearch;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalTime;
import java.util.ArrayList;
import static org.springframework.util.StringUtils.hasText;

@Data
@RequiredArgsConstructor
public class MovieSpec implements Specification<Movie> {

  private final MovieSearch movieShearchQuery;

  @Override
  public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    var predicates = new ArrayList<Predicate>();

    predicates.add(criteriaBuilder.equal(root.get("active"), true));


    if (movieShearchQuery.getMovieId() != null) {
      predicates.add(criteriaBuilder.equal(root.get("id"), movieShearchQuery.getMovieId()));
    }

    if (hasText(movieShearchQuery.getTitle())) {
      predicates.add(criteriaBuilder.like(root.get("title"), movieShearchQuery.getTitle() + "%"));
    }

    if (hasText(movieShearchQuery.getDescription())) {
      predicates.add(criteriaBuilder.like(root.get("description"), movieShearchQuery.getDescription() + "%"));
    }

    if (movieShearchQuery.getReleaseDateFrom() != null) {
      predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("releaseDate"), movieShearchQuery.getReleaseDateFrom().atStartOfDay()));
    }

    if (movieShearchQuery.getReleaseDateTo() != null) {
      predicates.add(criteriaBuilder.lessThan(root.get("releaseDate"), movieShearchQuery.getReleaseDateTo().atTime(LocalTime.MAX)));
    }

    if (hasText(movieShearchQuery.getImdbId())) {
      predicates.add(criteriaBuilder.equal(root.get("imdbId"), movieShearchQuery.getImdbId()));
    }

    if (hasText(movieShearchQuery.getImdbRating())) {
      predicates.add(criteriaBuilder.equal(root.get("imdbRating"), movieShearchQuery.getImdbRating()));
    }

    if (movieShearchQuery.getAgeRestriction() != null) {
      predicates.add(criteriaBuilder.equal(root.get("ageRestriction"), movieShearchQuery.getAgeRestriction()));
    }

    return predicates.stream().reduce(criteriaBuilder::and).orElse(criteriaBuilder.and());
  }
}
