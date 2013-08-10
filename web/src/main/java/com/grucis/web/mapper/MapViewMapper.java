package com.grucis.web.mapper;

import com.grucis.dev.model.raw.atomic.LS2Map;
import com.grucis.web.view.MapView;
import org.springframework.stereotype.Component;

@Component
public final class MapViewMapper extends ViewMapper<LS2Map, MapView> {
  @Override
  public MapView map(LS2Map model) {
    MapView ret = new MapView();

    ret.setId(model.getId());
    ret.setName(model.getName());
    ret.setEast(model.getEast());
    ret.setSouth(model.getSouth());

    return ret;
  }
}
