package com.example.cinme_service.v1.domain.mapper;

import com.example.cinme_service.v1.domain.SeatReservation;
import com.example.cinme_service.v1.domain.dto.SeatBookResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper
public abstract class SeatReservationMapper {

  @Mapping(source = "id", target = "id")
  @Mapping(source = "seat.id", target = "seatId")
  @Mapping(source = "seat.seatNumber", target = "seatNumber")
  @Mapping(source = "seat.colorCode", target = "colorCode")
  @Mapping(source = "seat.seatCondition", target = "seatCondition")
  public abstract SeatBookResp.ReservationResp toReservationResp(SeatReservation reservation);

  public abstract List<SeatBookResp.ReservationResp> toReservationRespList(List<SeatReservation> reservations);
}
