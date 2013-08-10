package com.grucis.dev.model.export.sprite;

import java.util.List;
import java.util.Map;

public final class AnimationSpriteSheetIndex {
  private Map<String, AnimationDef> animations;
  private List<List<Integer>> frames;

  public Map<String, AnimationDef> getAnimations() {
    return animations;
  }

  public void setAnimations(Map<String, AnimationDef> animations) {
    this.animations = animations;
  }

  public List<List<Integer>> getFrames() {
    return frames;
  }

  public void setFrames(List<List<Integer>> frames) {
    this.frames = frames;
  }
}
