package com.grucis.dev.exporter;

import com.grucis.dev.model.export.bitmap.OffsetBitmap;
import com.grucis.dev.model.setting.BitmapExportSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class BitmapExporter extends Exporter<OffsetBitmap> {

  @Autowired
  public BitmapExportSetting bitmapExportSetting;

  @Override
  public void export(OffsetBitmap model) throws Exception {
    String parent = bitmapExportSetting.getPath() + "\\" + model.getName();
    String path = parent + "\\" + model.getId();
    prepareFolder(parent);
    writeImage(model.getImage(), path + "." + bitmapExportSetting.getFormat());
    writeObject(model.getIndex(), path + ".json");
  }
}
