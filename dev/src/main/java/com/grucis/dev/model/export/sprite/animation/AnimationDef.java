package com.grucis.dev.model.export.sprite.animation;

import java.util.List;

public final class AnimationDef {
  private List<Integer> frames;
  private boolean next;
  private int frequency;

  public List<Integer> getFrames() {
    return frames;
  }

  public void setFrames(List<Integer> frames) {
    this.frames = frames;
  }

  public boolean getNext() {
    return next;
  }

  public void setNext(boolean next) {
    this.next = next;
  }

  public boolean isNext() {
    return next;
  }

  public int getFrequency() {
    return frequency;
  }

  public void setFrequency(int frequency) {
    this.frequency = frequency;
  }
}
