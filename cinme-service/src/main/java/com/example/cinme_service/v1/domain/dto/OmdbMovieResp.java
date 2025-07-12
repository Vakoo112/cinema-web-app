package com.example.cinme_service.v1.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OmdbMovieResp {

  @JsonProperty("Title")
  private String title;
  @JsonProperty("Year")
  private String year;
  @JsonProperty("imdbID")
  private String imdbId;
  @JsonProperty("imdbRating")
  private String imdbRating;
  @JsonProperty("imdbVotes")
  private String imdbVotes;
  @JsonProperty("Response")
  private String response;
  @JsonProperty("Error")
  private String error;
}
