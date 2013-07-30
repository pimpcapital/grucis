package com.grucis.dev.model.export;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public final class SpriteSheet extends ExportModel {

  private BufferedImage image;
  private List<PlacementReference> placements;
  private Map<String, AnimationReference> animations;

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

  public List<PlacementReference> getPlacements() {
    return placements;
  }

  public void setPlacements(List<PlacementReference> placements) {
    this.placements = placements;
  }

  public Map<String, AnimationReference> getAnimations() {
    return animations;
  }

  public void setAnimations(Map<String, AnimationReference> animations) {
    this.animations = animations;
  }

  public static final class PlacementReference {
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

  public static class AnimationReference {
    private List<Integer> frames;
    private String next;
    private double frequency;

    public List<Integer> getFrames() {
      return frames;
    }

    public void setFrames(List<Integer> frames) {
      this.frames = frames;
    }

    public String getNext() {
      return next;
    }

    public void setNext(String next) {
      this.next = next;
    }

    public double getFrequency() {
      return frequency;
    }

    public void setFrequency(double frequency) {
      this.frequency = frequency;
    }
  }
}
