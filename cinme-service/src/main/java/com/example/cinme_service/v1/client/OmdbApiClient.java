package com.example.cinme_service.v1.client;

import com.example.cinme_service.v1.domain.dto.OmdbMovieResp;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface OmdbApiClient {

  @GetExchange
  OmdbMovieResp getMovieByTitle(@RequestParam("t") String title, @RequestParam("apikey") String apiKey);

  @GetExchange
  OmdbMovieResp getMovieByImdbId(@RequestParam("i") String imdbId, @RequestParam("apikey") String apiKey);
}