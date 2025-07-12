package com.example.ticket_um.v1.domain.mapper;

import com.example.ticket_utils.v1.domain.model.Dc;
import com.example.ticket_um.v1.domain.commons.DictionaryResp;
import org.mapstruct.Mapper;
import java.util.List;
import java.util.Set;

@Mapper
public abstract class DictionaryMapper {
  public DictionaryMapper() {
  }

  public abstract DictionaryResp toDictionaryResp(Dc dictionary);

  public abstract List<DictionaryResp> toDictionaryRespList(List<? extends Dc> dictionaries);

  public abstract List<DictionaryResp> toDictionaryRespList(Set<? extends Dc> dictionaries);
}
