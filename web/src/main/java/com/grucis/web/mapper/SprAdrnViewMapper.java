package com.grucis.web.mapper;

import com.grucis.dev.model.raw.index.SprAdrn;
import com.grucis.web.view.SprAdrnView;
import org.springframework.stereotype.Component;

@Component
public final class SprAdrnViewMapper extends ViewMapper<SprAdrn, SprAdrnView> {

  @Override
  public SprAdrnView map(SprAdrn model) {
    SprAdrnView ret = new SprAdrnView();

    ret.setId(model.getId());
    ret.setActions(model.getActions());

    return ret;
  }
}
