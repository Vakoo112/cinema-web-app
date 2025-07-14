package com.example.cinme_service.v1.controller.external;

import com.example.cinme_service.v1.domain.dto.MovieShowResp;
import com.example.cinme_service.v1.domain.dto.MovieShowReq;
import com.example.cinme_service.v1.domain.dto.EditMovieShowReq;
import com.example.cinme_service.v1.service.MovieShowService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("external/v1/movie-shows")
@RestController
public class MovieShowController {

  private final MovieShowService movieShowService;

  @PreAuthorize("hasAuthority('ADD_MOVIE_SHOW')")
  @PostMapping
  public Resp<MovieShowResp> add(@RequestBody @Valid Req<MovieShowReq> req) {
    var resp = movieShowService.add(req.getData());
    return new Resp<>(resp);
  }

  @PreAuthorize("hasAuthority('ADD_MOVIE_SHOW')")
  @PutMapping("{id}")
  public Resp<MovieShowResp> edit(@PathVariable long id, @RequestBody @Valid Req<EditMovieShowReq> req) {
    var resp = movieShowService.edit(id, req.getData());
    return new Resp<>(resp);
  }

  @GetMapping("{id}")
  public Resp<MovieShowResp> get(@PathVariable("id") Long id) {
    var resp = movieShowService.get(id);
    return new Resp<>(resp);
  }

  @PostMapping("active")
  public PageResp<List<MovieShowResp>> getAllByActiveStatus(@RequestBody @Valid PageReq<Boolean> req) {
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
    return movieShowService.getAllByActiveStatus(req.getData(), pageable);
  }

}
