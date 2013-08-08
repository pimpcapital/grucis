package com.grucis.dev.io;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.grucis.dev.model.export.bitmap.BitmapIndex;
import com.grucis.dev.model.export.bitmap.OffsetBitmap;
import com.grucis.dev.model.export.sprite.animation.AnimationSprite;
import com.grucis.dev.model.export.sprite.animation.AnimationSpriteIndex;
import com.grucis.dev.model.setting.BitmapExportSetting;
import com.grucis.dev.model.setting.SpriteExportSetting;
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
  @Autowired
  public SpriteExportSetting spriteExportSetting;

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

  private String getBitmapPath(int id) {
    return bitmapExportSetting.getPath() + "\\" + OffsetBitmap.NAME + "\\" + id;
  }

  public BufferedImage loadBitmapImage(int id) {
    return loadImage(getBitmapPath(id) + "." + bitmapExportSetting.getFormat());
  }

  public BitmapIndex loadBitmapIndex(int id) {
    return loadObject(getBitmapPath(id) + ".json", BitmapIndex.class);
  }

  public OffsetBitmap loadOffsetBitmap(int id) {
    BufferedImage image = loadBitmapImage(id);
    if(image == null) return null;
    BitmapIndex index = loadBitmapIndex(id);
    if(index == null) return null;

    OffsetBitmap ret = new OffsetBitmap(id);
    ret.setImage(image);
    ret.setIndex(index);

    return ret;
  }

  private String getAnimationPath(int id) {
    return spriteExportSetting.getPath() + "\\" + AnimationSprite.NAME + "\\" + id;
  }

  public BufferedImage loadAnimationSpriteImage(int id) {
    return loadImage(getAnimationPath(id) + "." + spriteExportSetting.getFormat());
  }

  public AnimationSpriteIndex loadAnimationSpriteIndex(int id) {
    return loadObject(getAnimationPath(id) + ".json", AnimationSpriteIndex.class);
  }

  public AnimationSprite loadAnimationSprite(int id) {
    BufferedImage image = loadAnimationSpriteImage(id);
    if(image == null) return null;
    AnimationSpriteIndex index = loadAnimationSpriteIndex(id);
    if(index == null) return null;

    AnimationSprite ret = new AnimationSprite(id);
    ret.setImage(image);
    ret.setIndex(index);

    return ret;
  }
}
