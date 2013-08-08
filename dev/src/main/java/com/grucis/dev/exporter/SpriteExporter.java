package com.grucis.dev.exporter;

import com.grucis.dev.model.export.sprite.SpriteSheet;
import com.grucis.dev.model.setting.SpriteExportSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class SpriteExporter extends Exporter<SpriteSheet> {

  @Autowired
  public SpriteExportSetting spriteExportSetting;

  @Override
  public void export(SpriteSheet model) throws Exception {
    String parent = spriteExportSetting.getPath() + "\\" + model.getName();
    String path = parent + "\\" + model.getId();
    prepareFolder(parent);
    writeImage(model.getImage(), path + "." + spriteExportSetting.getFormat());
    writeObject(model.getIndex(), path + ".json");
  }

}
