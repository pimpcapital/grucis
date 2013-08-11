package com.grucis.dev.model.output;

import java.awt.image.BufferedImage;

public final class OffsetImage extends OutputModel {
  private int xOffset;
  private int yOffset;
  private int east;
  private int south;
  private boolean obstructive;
  private BufferedImage image;

  public OffsetImage(int id) {
    super(id);
  }

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

  public int getEast() {
    return east;
  }

  public void setEast(int east) {
    this.east = east;
  }

  public int getSouth() {
    return south;
  }

  public void setSouth(int south) {
    this.south = south;
  }

  public boolean getObstructive() {
    return obstructive;
  }

  public void setObstructive(boolean obstructive) {
    this.obstructive = obstructive;
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }
}
