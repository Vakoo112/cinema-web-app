package com.example.cinme_service.v1.domain.mapper;

import com.example.cinme_service.v1.domain.Hall;
import com.example.cinme_service.v1.domain.dto.EditHallReq;
import com.example.cinme_service.v1.domain.dto.HallReq;
import com.example.cinme_service.v1.domain.dto.HallResp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper
public abstract class HallMapper {

  public abstract Hall toHall(HallReq req);

  public abstract void update(EditHallReq req, @MappingTarget Hall hall);

  public abstract HallResp toHallResp(Hall hall);

  public abstract List<HallResp> toHallRespList(List<Hall> halls);
}