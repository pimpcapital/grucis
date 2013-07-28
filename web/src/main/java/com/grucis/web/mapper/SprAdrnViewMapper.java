package com.grucis.web.mapper;

import com.grucis.dev.model.raw.SprAdrn;
import com.grucis.dev.service.RawModelService;
import com.grucis.web.view.SprAdrnView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class SprAdrnViewMapper extends ViewMapper<SprAdrn, SprAdrnView> {

  @Autowired
  private RawModelService rawModelService;

  @Override
  public SprAdrnView map(SprAdrn model) {
    SprAdrnView ret = new SprAdrnView();

    ret.setId(model.getId());
    ret.setActions(model.getActions());

    return ret;
  }
}
