package com.grucis.web.view;

import java.util.List;
import java.util.Map;

public final class SpriteSheetView extends View {
  private Map<String, AnimationView> animations;
  private List<List<Integer>> frames;

  public Map<String, AnimationView> getAnimations() {
    return animations;
  }

  public void setAnimations(Map<String, AnimationView> animations) {
    this.animations = animations;
  }

  public List<List<Integer>> getFrames() {
    return frames;
  }

  public void setFrames(List<List<Integer>> frames) {
    this.frames = frames;
  }

  public static class AnimationView {
    List<Integer> frames;
    Object next;
    double frequency;

    public List<Integer> getFrames() {
      return frames;
    }

    public void setFrames(List<Integer> frames) {
      this.frames = frames;
    }

    public Object getNext() {
      return next;
    }

    public void setNext(Object next) {
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
