package com.grucis.dev.model.raw.data;

public final class Spr extends DataModel {
  private int direction;
  private int action;
  private int duration;
  private int length;
  private SprFrame[] frames;

  public int getDirection() {
    return direction;
  }

  public void setDirection(int direction) {
    this.direction = direction;
  }

  public int getAction() {
    return action;
  }

  public void setAction(int action) {
    this.action = action;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public SprFrame[] getFrames() {
    return frames;
  }

  public void setFrames(SprFrame[] frames) {
    this.frames = frames;
  }

  public static final class SprFrame {
    private int image;
    private String reference;

    public int getImage() {
      return image;
    }

    public void setImage(int image) {
      this.image = image;
    }

    public String getReference() {
      return reference;
    }

    public void setReference(String reference) {
      this.reference = reference;
    }
  }
}
