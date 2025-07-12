package com.example.cinme_service.v1.controller.external;

import com.example.cinme_service.v1.domain.dto.SeatReq;
import com.example.cinme_service.v1.domain.dto.SeatResp;
import com.example.cinme_service.v1.domain.dto.EditSeatReq;
import com.example.cinme_service.v1.domain.dto.SeatSearch;
import com.example.cinme_service.v1.service.SeatService;
import com.example.ticket_utils.v1.domain.dto.PageReq;
import com.example.ticket_utils.v1.domain.dto.PageResp;
import com.example.ticket_utils.v1.domain.dto.Req;
import com.example.ticket_utils.v1.domain.dto.Resp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("external/v1/seats")
@RestController
public class SeatController {

  private final SeatService seatService;

  @GetMapping("new-id")
  public Long getNewId() {
    return seatService.getNewId();
  }

  @PreAuthorize("hasAuthority('ADD_SEAT')")
  @PostMapping("hall/{hallId}")
  public Resp<SeatResp> add(@PathVariable long hallId, @RequestBody @Valid Req<SeatReq> req) {
    var resp = seatService.add(hallId, req.getData());
    return new Resp<>(resp);
  }

  @PreAuthorize("hasAuthority('ADD_SEAT')")
  @PutMapping("{id}")
  public Resp<SeatResp> edit(@PathVariable long id, @RequestBody @Valid Req<EditSeatReq> req) {
    var resp = seatService.edit(id, req.getData());
    return new Resp<>(resp);
  }

  @PreAuthorize("hasAuthority('ADD_SEAT')")
  @DeleteMapping("{id}")
  public void del(@PathVariable long id) {
    seatService.del(id);
  }

  @GetMapping("hall/{hallId}")
  public Resp<List<SeatResp>> getAllSeatsByHall(@PathVariable Long hallId) {
    var resp = seatService.getAllSeatsByHall(hallId);
    return new Resp<>(resp);
  }

  @GetMapping("available-seats/{movieShowId}")
  public Resp<List<SeatResp>> getAvailableSeats(@PathVariable Long movieShowId) {
    var resp = seatService.getAvailSeats(movieShowId);
    return new Resp<>(resp);
  }

  @GetMapping("{id}")
  public Resp<SeatResp> get(@PathVariable("id") Long id) {
    var resp = seatService.get(id);
    return new Resp<>(resp);
  }

  @GetMapping("price")
  public Resp<Long> get(@RequestParam List<Long> seatIds) {
    Long resp = seatService.getPrice(seatIds);
    return new Resp<>(resp);
  }

  @PostMapping("search")
  public PageResp<List<SeatResp>> search(@RequestBody @Valid PageReq<SeatSearch> req) {
    Sort sort;
    if (!CollectionUtils.isEmpty(req.getSort())) {
      var orders = new ArrayList<Sort.Order>();
      req.getSort().forEach(s -> {
        var order = new Sort.Order(s.getDirection(), s.getProperty());
        orders.add(order);
      });
      sort = Sort.by(orders);
    } else {
      sort = Sort.by("id").descending();
    }
    var pageable = PageRequest.of(req.getPage().getNumber(), req.getPage().getSize(), sort);
    return seatService.search(req.getData(), pageable);
  }
}
