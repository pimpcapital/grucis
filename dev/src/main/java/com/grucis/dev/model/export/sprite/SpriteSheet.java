package com.grucis.dev.model.export.sprite;

import java.awt.image.BufferedImage;

import com.grucis.dev.model.export.ExportModel;

public abstract class SpriteSheet<SI extends SpriteIndex> extends ExportModel {
  protected BufferedImage image;
  protected SI index;

  protected SpriteSheet(String name, int id) {
    super(name, id);
  }


  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

  public SI getIndex() {
    return index;
  }

  public void setIndex(SI index) {
    this.index = index;
  }
}
