package com.grucis.dev.model.output;

import java.util.Map;

public final class SpriteAnimationMap extends OutputModel {

  private Map<Direction, Map<Action, SpriteAnimation>> animationMap;

  public SpriteAnimationMap(int id) {
    super(id);
  }

  public Map<Direction, Map<Action, SpriteAnimation>> getAnimationMap() {
    return animationMap;
  }

  public void setAnimationMap(Map<Direction, Map<Action, SpriteAnimation>> animationMap) {
    this.animationMap = animationMap;
  }

  public static final class SpriteAnimation {
    private int duration;
    private int length;
    private int[] frames;

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

    public int[] getFrames() {
      return frames;
    }

    public void setFrames(int[] frames) {
      this.frames = frames;
    }
  }
}
