package com.grucis.dev.mapper.output;

import com.grucis.dev.model.output.SaMap;
import com.grucis.dev.model.raw.atomic.LS2Map;
import org.springframework.stereotype.Component;

@Component
public final class SaMapMapper extends OutputModelMapper<LS2Map, SaMap> {

  @Override
  public SaMap map(LS2Map source) {
    SaMap ret = new SaMap(source.getId());

    String name = source.getName();
    int length = name.indexOf('|');
    if(length != -1) name = name.substring(0, length);
    length = name.indexOf('\u0000');
    if(length != -1) name = name.substring(0, length);
    ret.setName(name);
    ret.setEast(source.getEast());
    ret.setSouth(source.getSouth());
    ret.setTiles(source.getTiles());
    ret.setObjects(source.getObjects());

    return ret;
  }
}
