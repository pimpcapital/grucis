package com.grucis.dev.mapper.dictionary;

import com.grucis.dev.model.dictionary.map.MapEntry;
import com.grucis.dev.model.raw.atomic.LS2Map;
import org.springframework.stereotype.Component;

@Component
public final class MapDictionaryMapper extends DictionaryModelMapper<LS2Map, MapEntry> {

  @Override
  public MapEntry map(LS2Map source) {
    MapEntry ret = new MapEntry(source.getId(), source.getPath());

    ret.setSouth(source.getSouth());
    ret.setEast(source.getEast());
    ret.setName(source.getName());

    return ret;
  }
}
