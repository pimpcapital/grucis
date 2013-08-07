package com.grucis.web.mapper;

import java.util.*;

import com.grucis.dev.model.export.sprite.animation.*;
import com.grucis.web.view.SpriteSheetView;
import org.springframework.stereotype.Component;

@Component
public final class SpriteSheetViewMapper extends ViewMapper<AnimationSprite, SpriteSheetView> {
  @Override
  public SpriteSheetView map(AnimationSprite model) {
    SpriteSheetView ret = new SpriteSheetView();

    AnimationIndex animationIndex = model.getSpriteIndex();

    List<AnimationFrame> frames = animationIndex.getFrames();
    List<List<Integer>> frameRefs = new ArrayList<List<Integer>>();
    for(AnimationFrame frame : frames) {
      List<Integer> placement = new ArrayList<Integer>();
      placement.add(frame.getX());
      placement.add(frame.getY());
      placement.add(frame.getWidth());
      placement.add(frame.getHeight());
      placement.add(0);
      placement.add(frame.getRegX());
      placement.add(frame.getRegY());
      frameRefs.add(placement);
    }
    ret.setFrames(frameRefs);

    Map<String, AnimationDef> animations = animationIndex.getAnimations();
    Map<String, List<Object>> animationRefs = new LinkedHashMap<String, List<Object>>();
    for(Map.Entry<String, AnimationDef> entry : animations.entrySet()) {
      AnimationDef animation = entry.getValue();
      List<Object> arrayDef = new ArrayList<Object>();
      arrayDef.add(Collections.min(animation.getFrames()));
      arrayDef.add(Collections.max(animation.getFrames()));
      arrayDef.add(animation.getNext());
      arrayDef.add(animation.getFrequency());
      animationRefs.put(entry.getKey(), arrayDef);
    }
    ret.setAnimations(animationRefs);

    return ret;
  }
}
