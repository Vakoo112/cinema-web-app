package com.example.cinme_service.v1.scheduler;

import com.example.cinme_service.v1.service.SeatBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatBookingSchedulerJobs {

  private final SeatBookingService seatBookingService;

  @Scheduled(fixedRate = 30000)
  public void expirePendingBookings() {
    seatBookingService.expirePendingBookings();
  }
}
