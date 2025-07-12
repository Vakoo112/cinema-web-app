package com.example.cinme_service.v1.domain.mapper;

import com.example.cinme_service.v1.domain.Seat;
import com.example.cinme_service.v1.domain.dto.EditSeatReq;
import com.example.cinme_service.v1.domain.dto.SeatReq;
import com.example.cinme_service.v1.domain.dto.SeatResp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public abstract class SeatMapper {

  public abstract Seat toSeat(SeatReq req);

  public abstract void update(EditSeatReq req, @MappingTarget Seat seat);

  public abstract SeatResp toSeatResp(Seat seat);

  public abstract List<SeatResp> toSeatRespList(List<Seat> seats);
}
