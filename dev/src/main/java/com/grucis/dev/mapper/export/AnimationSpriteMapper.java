package com.grucis.dev.mapper.export;

import java.awt.image.BufferedImage;
import java.util.*;

import com.grucis.dev.logic.sprite.SpriteSheetPacker;
import com.grucis.dev.model.export.bitmap.BitmapIndex;
import com.grucis.dev.model.export.bitmap.OffsetBitmap;
import com.grucis.dev.model.export.sprite.animation.AnimationDef;
import com.grucis.dev.model.export.sprite.animation.AnimationSprite;
import com.grucis.dev.model.export.sprite.animation.AnimationSpriteIndex;
import com.grucis.dev.model.output.Action;
import com.grucis.dev.model.output.AnimationMap;
import com.grucis.dev.model.output.Direction;
import com.grucis.dev.service.ExportModelService;
import com.grucis.dev.utils.sprite.RectangleImagePlaceholder;
import com.grucis.dev.utils.sprite.RectanglePlacement;
import com.grucis.dev.utils.sprite.SpriteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class AnimationSpriteMapper extends ExportModelMapper<AnimationMap, AnimationSprite> {

  @Autowired
  private ExportModelService exportModelService;

  @Override
  public AnimationSprite map(AnimationMap source) {
    AnimationSprite ret = new AnimationSprite(source.getId());

    Map<Direction, Map<Action, AnimationMap.SpriteAnimation>> animationMap = source.getAnimationMap();
    List<RectangleImagePlaceholder> placeholders = new ArrayList<RectangleImagePlaceholder>();
    Map<Integer, BufferedImage> images = new TreeMap<Integer, BufferedImage>();
    Map<Integer, BitmapIndex> indices = new TreeMap<Integer, BitmapIndex>();
    for(Map<Action, AnimationMap.SpriteAnimation> actionAnimationMaps : animationMap.values()) {
      for(AnimationMap.SpriteAnimation animation : actionAnimationMaps.values()) {
        for(int id : animation.getFrames()) {
          OffsetBitmap bitmap = exportModelService.getOffsetBitmap(id, false);
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

    List<List<Integer>> frames = new ArrayList<List<Integer>>();
    int placeRefCount = 0;
    Map<String, AnimationDef> animations = new LinkedHashMap<String, AnimationDef>();

    for(Map.Entry<Direction, Map<Action, AnimationMap.SpriteAnimation>> de : animationMap.entrySet()) {
      Direction direction = de.getKey();
      for(Map.Entry<Action, AnimationMap.SpriteAnimation> ae : de.getValue().entrySet()) {
        Action action = ae.getKey();
        AnimationMap.SpriteAnimation animation = ae.getValue();

        AnimationDef def = new AnimationDef();
        List<Integer> frameIndices = new ArrayList<Integer>();
        for(int id : animation.getFrames()) {
          BufferedImage image = images.get(id);
          RectanglePlacement placement = placements.get(id);
          List<Integer> frame = new ArrayList<Integer>();
          BitmapIndex index = indices.get(id);
          frame.add(placement.getX());
          frame.add(placement.getY());
          frame.add(image.getWidth());
          frame.add(image.getHeight());
          frame.add(0);
          frame.add(index.getRegX());
          frame.add(index.getRegY());

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

    AnimationSpriteIndex animationSpriteIndex = new AnimationSpriteIndex();
    animationSpriteIndex.setAnimations(animations);
    animationSpriteIndex.setFrames(frames);
    ret.setIndex(animationSpriteIndex);
    return ret;
  }
}
