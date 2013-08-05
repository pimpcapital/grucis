package com.grucis.dev.mapper.export;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import com.grucis.dev.logic.SpriteSheetPacker;
import com.grucis.dev.model.export.SpriteSheet;
import com.grucis.dev.model.output.*;
import org.springframework.stereotype.Component;

@Component
public final class SpriteSheetMapper extends ExportModelMapper<SpriteAnimationMap, SpriteSheet> {

  @Override
  public SpriteSheet map(SpriteAnimationMap source) {
    SpriteSheet ret = new SpriteSheet();

    Map<Direction, Map<Action, SpriteAnimationMap.SpriteAnimation>> animationMap = source.getAnimationMap();
    Set<BufferedImage> images = new LinkedHashSet<BufferedImage>();
    for(Map<Action, SpriteAnimationMap.SpriteAnimation> actionAnimationMaps : animationMap.values()) {
      for(SpriteAnimationMap.SpriteAnimation animation : actionAnimationMaps.values()) {
        for(OffsetImage image : animation.getFrames()) {
          images.add(image.getImage());
        }
      }
    }

    SpriteSheetPacker packer = new SpriteSheetPacker();
    packer.addImages(images);
    Map<BufferedImage, Rectangle> placements = packer.packImageRectangles();
    ret.setImage(packer.generateOutputImage());

    List<SpriteSheet.PlacementReference> placementRefs = new ArrayList<SpriteSheet.PlacementReference>();
    int placeRefCount = 0;
    Map<String, SpriteSheet.AnimationReference> animationRefs = new LinkedHashMap<String, SpriteSheet.AnimationReference>();

    for(Map.Entry<Direction, Map<Action, SpriteAnimationMap.SpriteAnimation>> de : animationMap.entrySet()) {
      Direction direction = de.getKey();
      for(Map.Entry<Action, SpriteAnimationMap.SpriteAnimation> ae : de.getValue().entrySet()) {
        Action action = ae.getKey();
        SpriteAnimationMap.SpriteAnimation animation = ae.getValue();

        SpriteSheet.AnimationReference aRef = new SpriteSheet.AnimationReference();
        List<Integer> frames = new ArrayList<Integer>();
        for(OffsetImage oi : animation.getFrames()) {
          BufferedImage image = oi.getImage();
          Rectangle placement = placements.get(image);
          SpriteSheet.PlacementReference pRef = new SpriteSheet.PlacementReference();
          pRef.setWidth(image.getWidth());
          pRef.setHeight(image.getHeight());
          pRef.setX(placement.x);
          pRef.setY(placement.y);
          pRef.setRegX(-oi.getxOffset());
          pRef.setRegY(-oi.getyOffset());

          placementRefs.add(pRef);
          frames.add(placeRefCount++);
        }
        aRef.setFrames(frames);
        aRef.setNext(action == Action.ANGRY
                       || action == Action.HAPPY
                       || action == Action.NOD
                       || action == Action.SAD
                       || action == Action.STAND
                       || action == Action.WALK
                       || action == Action.WAVE);
        aRef.setDuration(animation.getDuration());
        String aRefKey = direction.toString().toLowerCase() + "_" + action.toString().toLowerCase();
        animationRefs.put(aRefKey, aRef);
      }
    }

    ret.setPlacements(placementRefs);
    ret.setAnimations(animationRefs);
    return ret;
  }
}
