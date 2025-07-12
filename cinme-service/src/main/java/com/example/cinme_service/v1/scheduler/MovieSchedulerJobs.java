package com.example.cinme_service.v1.scheduler;

import com.example.cinme_service.v1.service.MovieService;
import com.example.cinme_service.v1.service.MovieShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieSchedulerJobs {

  private final MovieService movieService;
  private final MovieShowService movieShowService;

  @Scheduled(cron = "0 5 */12 * * *")
  public void fetchMovieRatingsEvery30Seconds() {
    movieService.fetchMovieRatingJob();
  }

  @Scheduled(cron = "0 5 */12 * * *")
  public void changeShowStatesEvery12Hours() {
    movieShowService.changeShowStateJob();
  }
}