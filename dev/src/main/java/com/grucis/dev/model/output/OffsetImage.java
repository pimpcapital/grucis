package com.grucis.dev.model.output;

import java.awt.image.BufferedImage;

public final class OffsetImage extends OutputModel {

  private int xOffset;
  private int yOffset;
  private BufferedImage image;

  public int getxOffset() {
    return xOffset;
  }

  public void setxOffset(int xOffset) {
    this.xOffset = xOffset;
  }

  public int getyOffset() {
    return yOffset;
  }

  public void setyOffset(int yOffset) {
    this.yOffset = yOffset;
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }
}
