package com.grucis.web.mapper;

import com.grucis.dev.model.setting.BitmapExportSetting;
import com.grucis.dev.service.RawModelService;
import com.grucis.web.view.BitmapExportSettingView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class BitmapExportSettingViewMapper extends ViewMapper<BitmapExportSetting, BitmapExportSettingView> {

  @Autowired
  RawModelService rawModelService;

  @Override
  public BitmapExportSettingView map(BitmapExportSetting model) {
    BitmapExportSettingView ret = new BitmapExportSettingView();

    ret.setPath(model.getPath());
    ret.setFormat(model.getFormat());
    ret.setTotal(rawModelService.getAllAdrns().size());

    return ret;
  }
}
