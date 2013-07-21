package com.grucis.dev.model.raw;

public final class Spr extends RawModel {
  private int direction;
  private int action;
  private int duration;
  private int frames;

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

  public int getFrames() {
    return frames;
  }

  public void setFrames(int frames) {
    this.frames = frames;
  }
}
