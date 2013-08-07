package com.grucis.dev.model.output;

import java.util.Map;

public final class SpriteAnimationMap extends OutputModel {

  private int id;
  private Map<Direction, Map<Action, SpriteAnimation>> animationMap;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
}
