package com.example.ticket_utils.v1.domain.mapper;

import com.example.ticket_utils.v1.domain.dto.DcResp;
import com.example.ticket_utils.v1.domain.model.Dc;
import org.mapstruct.Mapper;
import java.util.List;
import java.util.Set;

@Mapper
public abstract class DcMapper {

  public abstract DcResp toDictionaryResp(Dc dictionary);

  public abstract List<DcResp> toDictionaryRespList(List<? extends Dc> dictionaries);

  public abstract List<DcResp> toDictionaryRespList(Set<? extends Dc> dictionaries);
}
