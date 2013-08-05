package com.grucis.web.view;

import java.util.List;
import java.util.Map;

public final class SpriteSheetView extends View {
  private Map<String, List<Object>> animations;
  private List<List<Integer>> frames;

  public Map<String, List<Object>> getAnimations() {
    return animations;
  }

  public void setAnimations(Map<String, List<Object>> animations) {
    this.animations = animations;
  }

  public List<List<Integer>> getFrames() {
    return frames;
  }

  public void setFrames(List<List<Integer>> frames) {
    this.frames = frames;
  }
}
