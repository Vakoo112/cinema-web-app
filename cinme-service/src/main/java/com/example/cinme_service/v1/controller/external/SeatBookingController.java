package com.example.cinme_service.v1.controller.external;

import com.example.cinme_service.v1.domain.dto.EditSeatBookingReq;
import com.example.cinme_service.v1.domain.dto.SeatBookResp;
import com.example.cinme_service.v1.domain.dto.SeatBookingReq;
import com.example.cinme_service.v1.domain.dto.SeatBookingSearch;
import com.example.cinme_service.v1.service.SeatBookingService;
import com.example.ticket_utils.v1.domain.dto.PageReq;
import com.example.ticket_utils.v1.domain.dto.PageResp;
import com.example.ticket_utils.v1.domain.dto.Req;
import com.example.ticket_utils.v1.domain.dto.Resp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("external/v1/seat-booking")
@RestController
public class SeatBookingController {

  private final SeatBookingService seatBookingService;

  @PostMapping("pending")
  public ResponseEntity<SeatBookResp> createPendingBooking(@RequestBody SeatBookingReq req) {
    var response = seatBookingService.createPendingBooking(req.getSeatIds(), req.getMovieShowId(), req.getUserId());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PreAuthorize("hasAuthority('EDIT_BOOKING')")
  @PutMapping("{id}")
  public Resp<SeatBookResp> edit(@PathVariable long id, @RequestBody @Valid Req<EditSeatBookingReq> req) {
    var resp = seatBookingService.edit(id, req.getData());
    return new Resp<>(resp);
  }

  @PreAuthorize("hasAuthority('EDIT_BOOKING')")
  @GetMapping("{id}")
  public Resp<SeatBookResp> get(@PathVariable("id") Long id) {
    var resp = seatBookingService.get(id);
    return new Resp<>(resp);
  }

  @PreAuthorize("hasAuthority('EDIT_BOOKING')")
  @PostMapping("search")
  public PageResp<List<SeatBookResp>> search(@RequestBody @Valid PageReq<SeatBookingSearch> req) {
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
    return seatBookingService.search(req.getData(), pageable);
  }
}
