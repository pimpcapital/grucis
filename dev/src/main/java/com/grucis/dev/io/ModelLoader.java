package com.grucis.dev.io;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.grucis.dev.model.export.bitmap.BitmapIndex;
import com.grucis.dev.model.export.bitmap.OffsetBitmap;
import com.grucis.dev.model.setting.BitmapExportSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ModelLoader {

  private static final Logger LOG = LoggerFactory.getLogger(ModelLoader.class);
  private Gson gson = new Gson();

  @Autowired
  public BitmapExportSetting bitmapExportSetting;

  private BufferedImage loadImage(String path) {
    File file = new File(path);
    if(!file.exists()) return null;
    try {
      return ImageIO.read(file);
    } catch(IOException e) {
      LOG.error("Cannot load image from {}", path);
      return null;
    }
  }

  private <T> T loadObject(String path, Class<T> clazz) {
    File file = new File(path);
    if(!file.exists()) return null;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      StringBuilder sb = new StringBuilder();
      String line;
      while((line = reader.readLine()) != null) {
        sb.append(line);
      }
      return gson.fromJson(sb.toString(), clazz);
    } catch(IOException e) {
      LOG.error("Cannot load {} from {}", clazz.getCanonicalName(), path);
      return null;
    }
  }

  public OffsetBitmap loadOffsetBitmap(int id) {
    String parent = bitmapExportSetting.getPath() + "\\" + OffsetBitmap.NAME;
    String path = parent + "\\" + id;

    BufferedImage image = loadImage(path + "." + bitmapExportSetting.getFormat());
    if(image == null) return null;
    BitmapIndex index = loadObject(path + ".json", BitmapIndex.class);
    if(index == null) return null;

    OffsetBitmap ret = new OffsetBitmap(id);
    ret.setImage(image);
    ret.setIndex(index);

    return ret;
  }
}
