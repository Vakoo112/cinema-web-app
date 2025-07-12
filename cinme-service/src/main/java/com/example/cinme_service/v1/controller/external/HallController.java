package com.example.cinme_service.v1.controller.external;

import com.example.cinme_service.v1.domain.dto.HallResp;
import com.example.cinme_service.v1.domain.dto.HallReq;
import com.example.cinme_service.v1.domain.dto.EditHallReq;
import com.example.cinme_service.v1.domain.dto.HallSearch;
import com.example.cinme_service.v1.service.HallService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("external/v1/halls")
@RestController
public class HallController {

  private final HallService hallService;

  @PreAuthorize("hasAuthority('ADD_HALL')")
  @PostMapping
  public Resp<HallResp> add(@RequestBody @Valid Req<HallReq> req) {
    var resp = hallService.add(req.getData());
    return new Resp<>(resp);
  }

  @PreAuthorize("hasAuthority('ADD_HALL')")
  @PutMapping("{id}")
  public Resp<HallResp> edit(@PathVariable long id, @RequestBody @Valid Req<EditHallReq> req) {
    var resp = hallService.edit(id, req.getData());
    return new Resp<>(resp);
  }

  @PreAuthorize("hasAuthority('ADD_HALL')")
  @DeleteMapping("{id}")
  public void del(@PathVariable long id) {
    hallService.del(id);
  }

  @GetMapping("{id}")
  public Resp<HallResp> get(@PathVariable("id") Long id) {
    var resp = hallService.get(id);
    return new Resp<>(resp);
  }

  @PostMapping("search")
  public PageResp<List<HallResp>> search(@RequestBody @Valid PageReq<HallSearch> req) {
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
    return hallService.search(req.getData(), pageable);
  }
}
