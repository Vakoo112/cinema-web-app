package com.example.cinme_service.v1.repository;

import com.example.cinme_service.v1.domain.MovieShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovieShowRepo extends JpaRepository<MovieShow, Long> {

  @Query("""
         select m from MovieShow m
          where m.active = :active""")
  Page<MovieShow> findAllByActiveStatus(boolean active, Pageable pageable);

  @Query("select m from MovieShow m where m.active = true and m.endTime <= :now")
  List<MovieShow> findAllEndedShows(LocalDateTime now);

  Optional<MovieShow> findByIdAndActiveTrue(Long movieShowId);
}
