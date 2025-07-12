package com.example.cinme_service.v1.domain.mapper;

import com.example.cinme_service.v1.domain.SeatBooking;
import com.example.cinme_service.v1.domain.dto.SeatBookResp;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(uses = {MovieShowMapper.class, SeatReservationMapper.class})
public abstract class SeatBookingMapper {

  public abstract SeatBookResp toSeatBookResp(SeatBooking seatBooking);

  public abstract List<SeatBookResp> toSeatBookRespList(List<SeatBooking> seatBooking);
}
