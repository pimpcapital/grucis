package com.grucis.dev.exporter;

import com.grucis.dev.model.export.sprite.AnimationSpriteSheet;
import com.grucis.dev.model.setting.AnimationExportSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class AnimationExporter extends Exporter<AnimationSpriteSheet> {

  @Autowired
  public AnimationExportSetting animationExportSetting;

  @Override
  public void export(AnimationSpriteSheet model) throws Exception {
    String parent = animationExportSetting.getPath();
    String path = parent + "\\" + model.getId();
    prepareFolder(parent);
    writeImage(model.getImage(), path + "." + animationExportSetting.getFormat());
    writeObject(model.getIndex(), path + ".json");
  }

}
