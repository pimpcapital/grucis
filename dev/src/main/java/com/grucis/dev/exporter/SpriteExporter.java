package com.grucis.dev.exporter;

import java.awt.image.BufferedImage;
import java.io.*;

import com.grucis.dev.model.export.sprite.SpriteSheet;
import com.grucis.dev.model.setting.SpriteExportSetting;
import com.grucis.dev.utils.image.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class SpriteExporter extends Exporter<SpriteSheet> {

  @Autowired
  public SpriteExportSetting spriteExportSetting;

  @Override
  public void export(SpriteSheet model) throws Exception {
    String parent = spriteExportSetting.getPath() + "\\" + model.getName();
    String path = parent + "\\" + model.getIndex();
    prepareFolder(parent);

    BufferedImage image = model.getImage();
    byte[] bytes = ImageUtils.toBytes(image);
    File file = new File(path + "." + spriteExportSetting.getFormat());

    if(file.exists()) file.delete();
    FileOutputStream output = new FileOutputStream(file);
    output.write(bytes);
    output.close();

    String placements = gson().toJson(model.getIndex());
    file = new File(path + ".json");
    if(file.exists()) file.delete();
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    writer.write(placements);
    writer.close();
  }

}
