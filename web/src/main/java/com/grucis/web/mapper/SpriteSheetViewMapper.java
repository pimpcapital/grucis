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
    Map<String, SpriteSheetView.AnimationView> animations = new LinkedHashMap<String, SpriteSheetView.AnimationView>();
    for(Map.Entry<String, SpriteSheet.AnimationReference> entry : aRefs.entrySet()) {
      SpriteSheet.AnimationReference aRef = entry.getValue();
      SpriteSheetView.AnimationView aView = new SpriteSheetView.AnimationView();
      aView.setFrames(aRef.getFrames());
      aView.setNext(aRef.getNext());
      aView.setFrequency(1000d / aRef.getDuration() * aRef.getFrames().size());
      animations.put(entry.getKey(), aView);
    }
    ret.setAnimations(animations);

    return ret;
  }
}
