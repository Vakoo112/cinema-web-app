package com.example.cinme_service.v1.domain.mapper;

import com.example.cinme_service.v1.domain.MovieShow;
import com.example.cinme_service.v1.domain.dto.*;
import com.example.cinme_service.v1.service.HallService;
import com.example.cinme_service.v1.service.MovieService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Mapper(uses = {HallMapper.class, MovieMapper.class})
public abstract class MovieShowMapper {

  @Autowired
  protected MovieService movieService;
  @Autowired
  protected HallService hallService;

  public abstract MovieShow toMovieShow(MovieShowReq req);

  @AfterMapping
  protected void afterToMovieShow(MovieShowReq req, @MappingTarget MovieShow movieShow) {
    movieShow.setMovie(movieService.findById(req.getMovieId()));
    movieShow.setHall(hallService.findById(req.getHallId()));
  }

  public abstract void update(EditMovieShowReq req, @MappingTarget MovieShow movieShow);

  public abstract MovieShowResp toMovieShowResp(MovieShow movieShow);

  public abstract List<MovieShowResp> toMovieShowRespList(List<MovieShow> movieShows);
}
