package com.grucis.dev.model.output;

public final class SpriteAnimation extends OutputModel {
  private int duration;
  private int length;
  private OffsetImage[] frames;

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

  public OffsetImage[] getFrames() {
    return frames;
  }

  public void setFrames(OffsetImage[] frames) {
    this.frames = frames;
  }
}
