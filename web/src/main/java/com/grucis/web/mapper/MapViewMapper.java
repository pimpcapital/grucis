package com.grucis.web.mapper;

import com.grucis.dev.model.dictionary.map.MapEntry;
import com.grucis.web.view.MapView;
import org.springframework.stereotype.Component;

@Component
public final class MapViewMapper extends ViewMapper<MapEntry, MapView> {
  @Override
  public MapView map(MapEntry model) {
    MapView ret = new MapView();

    ret.setId(model.getId());
    ret.setName(model.getName());
    ret.setEast(model.getEast());
    ret.setSouth(model.getSouth());

    return ret;
  }
}
