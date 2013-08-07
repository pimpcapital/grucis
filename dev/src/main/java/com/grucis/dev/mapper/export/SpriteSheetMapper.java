package com.grucis.dev.mapper.export;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import com.grucis.dev.logic.SpriteSheetPacker;
import com.grucis.dev.model.export.sprite.animation.*;
import com.grucis.dev.model.output.*;
import org.springframework.stereotype.Component;

@Component
public final class SpriteSheetMapper extends ExportModelMapper<SpriteAnimationMap, AnimationSprite> {

  @Override
  public AnimationSprite map(SpriteAnimationMap source) {
    AnimationSprite ret = new AnimationSprite();

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

    List<AnimationFrame> frames = new ArrayList<AnimationFrame>();
    int placeRefCount = 0;
    Map<String, AnimationDef> animations = new LinkedHashMap<String, AnimationDef>();

    for(Map.Entry<Direction, Map<Action, SpriteAnimationMap.SpriteAnimation>> de : animationMap.entrySet()) {
      Direction direction = de.getKey();
      for(Map.Entry<Action, SpriteAnimationMap.SpriteAnimation> ae : de.getValue().entrySet()) {
        Action action = ae.getKey();
        SpriteAnimationMap.SpriteAnimation animation = ae.getValue();

        AnimationDef def = new AnimationDef();
        List<Integer> frameIndices = new ArrayList<Integer>();
        for(OffsetImage oi : animation.getFrames()) {
          BufferedImage image = oi.getImage();
          Rectangle placement = placements.get(image);
          AnimationFrame frame = new AnimationFrame();
          frame.setWidth(image.getWidth());
          frame.setHeight(image.getHeight());
          frame.setX(placement.x);
          frame.setY(placement.y);
          frame.setRegX(-oi.getxOffset());
          frame.setRegY(-oi.getyOffset());

          frames.add(frame);
          frameIndices.add(placeRefCount++);
        }
        def.setFrames(frameIndices);
        def.setNext(action == Action.ANGRY
                      || action == Action.HAPPY
                      || action == Action.NOD
                      || action == Action.SAD
                      || action == Action.STAND
                      || action == Action.WALK
                      || action == Action.WAVE);
        def.setFrequency((int)Math.ceil(((double)animation.getDuration()) * 30 / frameIndices.size() / 1000));
        String aRefKey = direction.toString().toLowerCase() + "_" + action.toString().toLowerCase();
        animations.put(aRefKey, def);
      }
    }

    ret.setIndex(source.getId());
    AnimationIndex animationIndex = new AnimationIndex();
    animationIndex.setAnimations(animations);
    animationIndex.setFrames(frames);
    ret.setSpriteIndex(animationIndex);
    return ret;
  }
}
