package com.example.cinme_service.v1.service;

import com.example.cinme_service.v1.domain.MovieShow;
import com.example.cinme_service.v1.domain.dto.EditMovieShowReq;
import com.example.cinme_service.v1.domain.dto.MovieShowReq;
import com.example.cinme_service.v1.domain.dto.MovieShowResp;
import com.example.cinme_service.v1.domain.mapper.MovieShowMapper;
import com.example.cinme_service.v1.repository.MovieShowRepo;
import com.example.ticket_utils.v1.domain.dto.PageResp;
import com.example.ticket_utils.v1.exception.ForbiddenException;
import com.example.ticket_utils.v1.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import static com.example.cinme_service.v1.exception.ErrorKeyword.MOVIE_SHOW_NOT_FOUND;
import static com.example.cinme_service.v1.exception.ErrorKeyword.MOVIE_SHOW_NOT_FOUND_OR_NOT_EXISTS;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieShowService {

  private final MovieShowRepo movieShowRepo;
  private final MovieShowMapper movieShowMapper;
  private final MovieService movieService;

  @Transactional
  public MovieShowResp add(MovieShowReq req) {
    var movieShow = movieShowMapper.toMovieShow(req);
    var movie = movieService.findById(req.getMovieId());

    var startTime = movieShow.getStartTime();
    var duration = movie.getDurationInMinutes();
    
    movieShow.setEndTime(startTime.plusMinutes(duration));
    movieShowRepo.saveAndFlush(movieShow);
    return movieShowMapper.toMovieShowResp(movieShow);
  }

  @Transactional
  public MovieShowResp edit(long id, EditMovieShowReq req) {
    var movieShow = findById(id);
    movieShowMapper.update(req, movieShow);
    movieShowRepo.flush();
    return movieShowMapper.toMovieShowResp(movieShow);
  }

  public MovieShowResp get(Long id) {
    var movieShow = findById(id);
    return movieShowMapper.toMovieShowResp(movieShow);
  }

  public PageResp<List<MovieShowResp>> getAllByActiveStatus(boolean active, Pageable page) {
    var movieShowsPage = movieShowRepo.findAllByActiveStatus(active, page);
    var movieShowResp = movieShowMapper.toMovieShowRespList(movieShowsPage.getContent());
    var pageResp = new PageResp.PageSizeResp(
        movieShowsPage.getTotalElements(),
        movieShowsPage.getTotalPages(),
        movieShowsPage.getSize(),
        movieShowsPage.getNumber());
    return new PageResp<>(movieShowResp, pageResp);
  }

  @Transactional
  public void changeShowStateJob() {
    var endedShows = movieShowRepo.findAllEndedShows(LocalDateTime.now());

    for (MovieShow show : endedShows) {
      show.setActive(false);
    }
    
    movieShowRepo.flush();
  }

  public MovieShow getActiveMovieShowById(Long movieShowId) {
    return movieShowRepo.findByIdAndActiveTrue(movieShowId)
        .orElseThrow(() -> new ForbiddenException(MOVIE_SHOW_NOT_FOUND_OR_NOT_EXISTS));
  }

  public MovieShow findById(long id) {
    return movieShowRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(MOVIE_SHOW_NOT_FOUND));
  }
}
