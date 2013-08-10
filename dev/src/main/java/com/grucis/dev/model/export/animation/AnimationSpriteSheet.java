package com.grucis.dev.model.export.animation;

import java.awt.image.BufferedImage;

import com.grucis.dev.model.export.ExportModel;

public final class AnimationSpriteSheet extends ExportModel {
  protected BufferedImage image;
  protected AnimationSpriteSheetIndex index;

  public AnimationSpriteSheet(int id) {
    super(id);
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

  public AnimationSpriteSheetIndex getIndex() {
    return index;
  }

  public void setIndex(AnimationSpriteSheetIndex index) {
    this.index = index;
  }
}
