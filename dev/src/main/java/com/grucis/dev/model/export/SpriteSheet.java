package com.grucis.dev.model.export;

import java.awt.image.BufferedImage;
import java.util.List;

public final class SpriteSheet extends ExportModel {

  private BufferedImage image;
  private List<ImageReference> references;

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

  public List<ImageReference> getReferences() {
    return references;
  }

  public void setReferences(List<ImageReference> references) {
    this.references = references;
  }

  public static final class ImageReference {
    private int x;
    private int y;
    private int width;
    private int height;
    private int regX;
    private int regY;

    public int getX() {
      return x;
    }

    public void setX(int x) {
      this.x = x;
    }

    public int getY() {
      return y;
    }

    public void setY(int y) {
      this.y = y;
    }

    public int getWidth() {
      return width;
    }

    public void setWidth(int width) {
      this.width = width;
    }

    public int getHeight() {
      return height;
    }

    public void setHeight(int height) {
      this.height = height;
    }

    public int getRegX() {
      return regX;
    }

    public void setRegX(int regX) {
      this.regX = regX;
    }

    public int getRegY() {
      return regY;
    }

    public void setRegY(int regY) {
      this.regY = regY;
    }
  }
}
