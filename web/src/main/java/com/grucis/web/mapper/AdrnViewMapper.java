package com.grucis.web.mapper;

import com.grucis.dev.model.raw.Adrn;
import com.grucis.web.view.AdrnView;
import org.springframework.stereotype.Component;

@Component
public final class AdrnViewMapper extends ViewMapper<Adrn, AdrnView> {
  @Override
  public AdrnView map(Adrn model) {
    AdrnView ret = new AdrnView();

    ret.setId(model.getId());
    ret.setMap(model.getMap());
    ret.setWidth(model.getWidth());
    ret.setHeight(model.getHeight());
    ret.setxOffset(model.getxOffset());
    ret.setyOffset(model.getyOffset());

    return ret;
  }
}
