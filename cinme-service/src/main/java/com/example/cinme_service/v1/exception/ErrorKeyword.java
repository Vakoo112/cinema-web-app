package com.example.cinme_service.v1.exception;

import com.example.ticket_utils.v1.exception.Error;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorKeyword implements Error {

  MOVIE_NOT_FOUND("Movie Not Found!"),
  MOVIE_SHOW_NOT_FOUND("Movie Show Not Found!"),
  MOVIE_SHOW_NOT_FOUND_OR_NOT_EXISTS("Movie Show Not Found or Not Exists!"),
  SEAT_NOT_FOUND("Seat Not Found!"),
  SEAT_RESERVATION_NOT_FOUND("Seat Reservation Not Found!"),
  MAX_SEAT_RESTRICTION("Max 4 seats allowed"),
  SEAT_UNAVAILABLE("Seat Unavailable!"),
  SEAT_NOT_FOUND_OR_NOT_EXISTS("Seat Not Found or Not Exists!"),
  HALL_NOT_FOUND("Hall Not Found!"),
  HALL_IS_FULL("Hall capacity is full. Cannot add more seats!"),
  SEAT_ALREADY_EXISTS("Seat Already Exists!"),
  CAN_NOT_ADD_SEAT("Can Not Add Seat!"),
  SEAT_BOOKING_NOT_FOUND("Seat Booking Not Found!");

  private final String message;
}
