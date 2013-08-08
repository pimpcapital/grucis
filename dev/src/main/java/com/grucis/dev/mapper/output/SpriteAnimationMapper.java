package com.grucis.dev.mapper.output;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.grucis.dev.model.output.Action;
import com.grucis.dev.model.output.Direction;
import com.grucis.dev.model.output.SpriteAnimationMap;
import com.grucis.dev.model.raw.Spr;
import com.grucis.dev.model.raw.SprAdrn;
import com.grucis.dev.service.RawModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class SpriteAnimationMapper extends OutputModelMapper<SprAdrn, SpriteAnimationMap> {

  @Autowired
  private RawModelService rawModelService;

  @Override
  public SpriteAnimationMap map(SprAdrn source) {
    SpriteAnimationMap ret = new SpriteAnimationMap(source.getId());

    List<Spr> sprs = rawModelService.getSprs(source);
    Map<Direction, Map<Action, SpriteAnimationMap.SpriteAnimation>> animationMap = new LinkedHashMap<Direction, Map<Action, SpriteAnimationMap.SpriteAnimation>>();
    for(Spr spr : sprs) {
      SpriteAnimationMap.SpriteAnimation animation = new SpriteAnimationMap.SpriteAnimation();
      int length = spr.getLength();
      animation.setLength(length);
      animation.setDuration(spr.getDuration());
      Spr.SprFrame[] frames = spr.getFrames();
      int[] images = new int[length];
      for(int i = 0; i < length; i++) {
        images[i] = frames[i].getImage();
      }
      animation.setFrames(images);

      Direction direction = Direction.values()[spr.getDirection()];
      Action action = Action.values()[spr.getAction()];
      Map<Action, SpriteAnimationMap.SpriteAnimation> actionSpriteAnimationMap = animationMap.get(direction);
      if(actionSpriteAnimationMap == null) {
        actionSpriteAnimationMap = new LinkedHashMap<Action, SpriteAnimationMap.SpriteAnimation>();
        animationMap.put(direction, actionSpriteAnimationMap);
      }
      actionSpriteAnimationMap.put(action, animation);
    }
    ret.setAnimationMap(animationMap);

    return ret;
  }
}
