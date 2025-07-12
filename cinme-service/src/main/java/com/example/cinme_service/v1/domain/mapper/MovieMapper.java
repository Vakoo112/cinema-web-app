package com.example.cinme_service.v1.domain.mapper;

import com.example.cinme_service.v1.domain.Movie;
import com.example.cinme_service.v1.domain.dto.EditMovieReq;
import com.example.cinme_service.v1.domain.dto.MovieReq;
import com.example.cinme_service.v1.domain.dto.MovieResp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper
public abstract class MovieMapper {

  public abstract Movie toMovie(MovieReq req);

  public abstract void update(EditMovieReq req, @MappingTarget Movie movie);

  public abstract MovieResp toMovieResp(Movie movie);

  public abstract List<MovieResp> toMovieRespList(List<Movie> movies);
}
