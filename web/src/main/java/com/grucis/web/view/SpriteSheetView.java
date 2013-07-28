package com.grucis.web.view;

import java.util.List;
import java.util.Map;

public final class SpriteSheetView extends View {
  private Map<String, List<Integer>> animations;
  private List<String> images;
  private List<List<Integer>> frames;

  public Map<String, List<Integer>> getAnimations() {
    return animations;
  }

  public void setAnimations(Map<String, List<Integer>> animations) {
    this.animations = animations;
  }

  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  public List<List<Integer>> getFrames() {
    return frames;
  }

  public void setFrames(List<List<Integer>> frames) {
    this.frames = frames;
  }
}
