package com.grucis.dev.mapper.export;

import java.awt.image.BufferedImage;
import java.util.*;

import com.grucis.dev.logic.sprite.SpriteSheetPacker;
import com.grucis.dev.model.export.bitmap.BitmapIndex;
import com.grucis.dev.model.export.bitmap.OffsetBitmap;
import com.grucis.dev.model.export.sprite.animation.*;
import com.grucis.dev.model.output.Action;
import com.grucis.dev.model.output.Direction;
import com.grucis.dev.model.output.SpriteAnimationMap;
import com.grucis.dev.service.ExportModelService;
import com.grucis.dev.utils.sprite.RectangleImagePlaceholder;
import com.grucis.dev.utils.sprite.RectanglePlacement;
import com.grucis.dev.utils.sprite.SpriteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class SpriteSheetMapper extends ExportModelMapper<SpriteAnimationMap, AnimationSprite> {

  @Autowired
  private ExportModelService exportModelService;

  @Override
  public AnimationSprite map(SpriteAnimationMap source) {
    AnimationSprite ret = new AnimationSprite(source.getId());

    Map<Direction, Map<Action, SpriteAnimationMap.SpriteAnimation>> animationMap = source.getAnimationMap();
    List<RectangleImagePlaceholder> placeholders = new ArrayList<RectangleImagePlaceholder>();
    Map<Integer, BufferedImage> images = new TreeMap<Integer, BufferedImage>();
    Map<Integer, BitmapIndex> indices = new TreeMap<Integer, BitmapIndex>();
    for(Map<Action, SpriteAnimationMap.SpriteAnimation> actionAnimationMaps : animationMap.values()) {
      for(SpriteAnimationMap.SpriteAnimation animation : actionAnimationMaps.values()) {
        for(int id : animation.getFrames()) {
          OffsetBitmap bitmap = exportModelService.getOffsetBitmap(id);
          BufferedImage image = bitmap.getImage();
          BitmapIndex index = bitmap.getIndex();
          placeholders.add(new RectangleImagePlaceholder(id, image.getWidth(), image.getHeight()));
          images.put(id, image);
          indices.put(id, index);
        }
      }
    }

    SpriteSheetPacker packer = new SpriteSheetPacker();
    packer.addImages(placeholders);
    Map<Integer, RectanglePlacement> placements = packer.packImageRectangles();
    ret.setImage(SpriteUtils.packImages(images, placements, packer.getOutputWidth(), packer.getOutputHeight()));

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
        for(int id : animation.getFrames()) {
          BufferedImage image = images.get(id);
          RectanglePlacement placement = placements.get(id);
          AnimationFrame frame = new AnimationFrame();
          frame.setWidth(image.getWidth());
          frame.setHeight(image.getHeight());
          frame.setX(placement.getX());
          frame.setY(placement.getY());
          BitmapIndex index = indices.get(id);
          frame.setRegX(index.getRegX());
          frame.setRegY(index.getRegY());

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

    AnimationIndex animationIndex = new AnimationIndex();
    animationIndex.setAnimations(animations);
    animationIndex.setFrames(frames);
    ret.setIndex(animationIndex);
    return ret;
  }
}
