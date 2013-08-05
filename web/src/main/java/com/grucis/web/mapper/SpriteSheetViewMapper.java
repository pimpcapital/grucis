package com.grucis.web.mapper;

import java.util.*;

import com.grucis.dev.model.export.SpriteSheet;
import com.grucis.web.view.SpriteSheetView;
import org.springframework.stereotype.Component;

@Component
public final class SpriteSheetViewMapper extends ViewMapper<SpriteSheet, SpriteSheetView> {
  @Override
  public SpriteSheetView map(SpriteSheet model) {
    SpriteSheetView ret = new SpriteSheetView();

    List<SpriteSheet.PlacementReference> pRefs = model.getPlacements();
    List<List<Integer>> frames = new ArrayList<List<Integer>>();
    for(SpriteSheet.PlacementReference pRef : pRefs) {
      List<Integer> placement = new ArrayList<Integer>();
      placement.add(pRef.getX());
      placement.add(pRef.getY());
      placement.add(pRef.getWidth());
      placement.add(pRef.getHeight());
      placement.add(0);
      placement.add(pRef.getRegX());
      placement.add(pRef.getRegY());
      frames.add(placement);
    }
    ret.setFrames(frames);

    Map<String, SpriteSheet.AnimationReference> aRefs = model.getAnimations();
    Map<String, List<Object>> animations = new LinkedHashMap<String, List<Object>>();
    for(Map.Entry<String, SpriteSheet.AnimationReference> entry : aRefs.entrySet()) {
      SpriteSheet.AnimationReference aRef = entry.getValue();
      List<Object> animation = new ArrayList<Object>();
      animation.add(Collections.min(aRef.getFrames()));
      animation.add(Collections.max(aRef.getFrames()));
      animation.add(aRef.getNext());
      animation.add(Math.ceil(((double)aRef.getDuration()) * 30 / aRef.getFrames().size() / 1000));
      animations.put(entry.getKey(), animation);
    }
    ret.setAnimations(animations);

    return ret;
  }
}
