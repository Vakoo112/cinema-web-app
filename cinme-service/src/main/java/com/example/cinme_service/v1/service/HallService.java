package com.example.cinme_service.v1.service;

import com.example.cinme_service.v1.domain.Hall;
import com.example.cinme_service.v1.domain.dto.HallResp;
import com.example.cinme_service.v1.domain.dto.HallReq;
import com.example.cinme_service.v1.domain.dto.EditHallReq;
import com.example.cinme_service.v1.domain.dto.HallSearch;
import com.example.cinme_service.v1.domain.mapper.HallMapper;
import com.example.cinme_service.v1.repository.HallRepo;
import com.example.cinme_service.v1.repository.HallSpec;
import com.example.ticket_utils.v1.domain.dto.PageResp;
import com.example.ticket_utils.v1.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static com.example.cinme_service.v1.exception.ErrorKeyword.HALL_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HallService {

  private final HallRepo hallRepo;
  private final HallMapper hallMapper;

  @Transactional
  public HallResp add(HallReq req) {
    var hall = hallMapper.toHall(req);
    hallRepo.saveAndFlush(hall);
    return hallMapper.toHallResp(hall);
  }

  @Transactional
  public HallResp edit(long id, EditHallReq req) {
    var hall = findById(id);
    hallMapper.update(req, hall);
    hallRepo.flush();
    return hallMapper.toHallResp(hall);
  }

  @Transactional
  public void del(long id) {
    var hall = findById(id);
    hall.setDeleted(true);
  }

  public HallResp get(long id) {
    var hall = findById(id);
    return hallMapper.toHallResp(hall);
  }

  public PageResp<List<HallResp>> search(HallSearch req, Pageable pageable) {
    var spec = new HallSpec(req);
    var hallPage = hallRepo.findAll(spec, pageable);
    var hallRespList = hallMapper.toHallRespList(hallPage.getContent());
    var pageResp = new PageResp.PageSizeResp(
        hallPage.getTotalElements(),
        hallPage.getTotalPages(),
        hallPage.getSize(),
        hallPage.getNumber());
    return new PageResp<>(hallRespList, pageResp);
  }

  public Hall findById(long id) {
    return hallRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(HALL_NOT_FOUND));
  }
}

