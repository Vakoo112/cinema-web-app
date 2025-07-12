package com.example.cinme_service.v1.controller.external;

import com.example.cinme_service.v1.domain.dto.EditMovieReq;
import com.example.cinme_service.v1.domain.dto.MovieReq;
import com.example.cinme_service.v1.domain.dto.MovieResp;
import com.example.cinme_service.v1.domain.dto.OmdbMovieResp;
import com.example.cinme_service.v1.domain.dto.MovieSearch;
import com.example.cinme_service.v1.service.MovieService;
import com.example.ticket_utils.v1.domain.dto.PageResp;
import com.example.ticket_utils.v1.domain.dto.PageReq;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("external/v1/movies")
@RestController
public class MovieController {

  private final MovieService movieService;

  @PreAuthorize("hasAuthority('ADD_MOVIE')")
  @PostMapping
  public Resp<MovieResp> add(@RequestBody @Valid Req<MovieReq> req) {
    var resp = movieService.add(req.getData());
    return new Resp<>(resp);
  }

  @PreAuthorize("hasAuthority('ADD_MOVIE')")
  @PutMapping("{id}")
  public Resp<MovieResp> edit(@PathVariable long id, @RequestBody @Valid Req<EditMovieReq> req) {
    var resp = movieService.edit(id, req.getData());
    return new Resp<>(resp);
  }

  @PreAuthorize("hasAuthority('ADD_MOVIE')")
  @PatchMapping("{id}/state/{active}")
  public Resp<MovieResp> chngState(@PathVariable long id, @PathVariable boolean active) {
    var resp = movieService.changeState(id, active);
    return new Resp<>(resp);
  }

  @GetMapping("{id}")
  public Resp<MovieResp> get(@PathVariable("id") Long id) {
    var resp = movieService.get(id);
    return new Resp<>(resp);
  }

  @GetMapping("by-id")
  public OmdbMovieResp getMovieByImdbId(@RequestParam String imdbId) {
    return movieService.getMovieByImdbId(imdbId);
  }

  @PostMapping("search")
  public PageResp<List<MovieResp>> search(@RequestBody @Valid PageReq<MovieSearch> req) {
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
    return movieService.search(req.getData(), pageable);
  }
}
