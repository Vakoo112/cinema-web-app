package com.example.cinme_service.v1.service;

import com.example.cinme_service.v1.client.OmdbApiClient;
import com.example.cinme_service.v1.domain.Movie;
import com.example.cinme_service.v1.domain.dto.EditMovieReq;
import com.example.cinme_service.v1.domain.dto.MovieReq;
import com.example.cinme_service.v1.domain.dto.MovieResp;
import com.example.cinme_service.v1.domain.dto.OmdbMovieResp;
import com.example.cinme_service.v1.domain.dto.MovieSearch;
import com.example.cinme_service.v1.domain.mapper.MovieMapper;
import com.example.cinme_service.v1.repository.MovieRepo;
import com.example.cinme_service.v1.repository.MovieSpec;
import com.example.ticket_utils.v1.domain.dto.PageResp;
import com.example.ticket_utils.v1.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.example.cinme_service.v1.exception.ErrorKeyword.MOVIE_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieService {

  private final MovieRepo movieRepo;
  private final MovieMapper movieMapper;
  private final OmdbApiClient omdbApiClient;

  @Value("${omdb.api.key}")
  private String apiKey;

  @Transactional
  public MovieResp add(MovieReq req) {
    var movie = movieMapper.toMovie(req);
    movieRepo.save(movie);
    return movieMapper.toMovieResp(movie);
  }

  @Transactional
  public MovieResp edit(long id, EditMovieReq req) {
    var movie = findById(id);
    movieMapper.update(req, movie);
    movieRepo.flush();
    return movieMapper.toMovieResp(movie);
  }

  public MovieResp changeState(long id, boolean active) {
    var movie = findById(id);
    movie.setActive(active);
    return movieMapper.toMovieResp(movie);
  }

  public MovieResp get(Long id) {
    var movie = findById(id);
    return movieMapper.toMovieResp(movie);
  }

  public OmdbMovieResp getMovieByImdbId(String imdbId) {
    return omdbApiClient.getMovieByImdbId(imdbId, apiKey);
  }

  public PageResp<List<MovieResp>> search(MovieSearch req, Pageable pageable) {
    var spec = new MovieSpec(req);
    var moviePage = movieRepo.findAll(spec, pageable);
    var movieRespList = movieMapper.toMovieRespList(moviePage.getContent());
    var pageResp = new PageResp.PageSizeResp(
        moviePage.getTotalElements(),
        moviePage.getTotalPages(),
        moviePage.getSize(),
        moviePage.getNumber());
    return new PageResp<>(movieRespList, pageResp);
  }

  @Transactional
  public void fetchMovieRatingJob() {
    var activeMovies = movieRepo.findAllByActiveTrue();

    activeMovies.forEach(movie -> {
      var imdbId = movie.getImdbId();
      if (imdbId != null && !imdbId.isBlank()) {
        try {
          var resp = getMovieByImdbId(imdbId);
          if (resp != null && resp.getImdbRating() != null) {
            movie.setImdbRating(resp.getImdbRating());
          }
        } catch (Exception e) {
          // log slf4
          System.err.println("Failed to fetch rating for IMDb ID " + imdbId + ": " + e.getMessage());
        }
      }
    });
  }

  public Movie findById(long id) {
    return movieRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND));
  }
}
