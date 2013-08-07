package com.grucis.dev.model.export.sprite;

import java.awt.image.BufferedImage;

import com.grucis.dev.model.export.ExportModel;

public abstract class SpriteSheet<SI extends SpriteIndex> extends ExportModel {
  private final Class<SI> indexClass;
  protected BufferedImage image;
  protected SI spriteIndex;

  protected SpriteSheet(String name, Class<SI> indexClass) {
    super(name);
    this.indexClass = indexClass;
  }

  public Class<SI> getIndexClass() {
    return indexClass;
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

  public SI getSpriteIndex() {
    return spriteIndex;
  }

  public void setSpriteIndex(SI spriteIndex) {
    this.spriteIndex = spriteIndex;
  }
}
