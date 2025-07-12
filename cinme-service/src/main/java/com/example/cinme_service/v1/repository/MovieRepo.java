package com.example.cinme_service.v1.repository;

import com.example.cinme_service.v1.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface MovieRepo extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

  List<Movie> findAllByActiveTrue();
}
