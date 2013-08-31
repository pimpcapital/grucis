package com.grucis.dev.mapper.output;

import com.grucis.dev.model.output.SaMap;
import com.grucis.dev.model.raw.atomic.LS2Map;
import org.springframework.stereotype.Component;

@Component
public final class SaMapMapper extends OutputModelMapper<LS2Map, SaMap> {

  @Override
  public SaMap map(LS2Map source) {
    SaMap ret = new SaMap(source.getId());

    ret.setName(source.getName());
    ret.setEast(source.getEast());
    ret.setSouth(source.getSouth());
    ret.setTiles(source.getTiles());
    ret.setObjects(source.getObjects());

    return ret;
  }
}
