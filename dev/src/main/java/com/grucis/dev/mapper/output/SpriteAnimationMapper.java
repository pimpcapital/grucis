package com.grucis.dev.mapper.output;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.grucis.dev.model.output.*;
import com.grucis.dev.model.raw.Adrn;
import com.grucis.dev.model.raw.Spr;
import com.grucis.dev.model.raw.SprAdrn;
import com.grucis.dev.service.RawModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class SpriteAnimationMapper extends OutputModelMapper<SprAdrn, SpriteAnimationMap> {

  @Autowired
  private RawModelService rawModelService;
  @Autowired
  private OffsetImageMapper offsetImageMapper;

  @Override
  public SpriteAnimationMap map(SprAdrn source) {
    SpriteAnimationMap ret = new SpriteAnimationMap();

    List<Spr> sprs = rawModelService.getSprs(source);
    Map<Direction, Map<Action, SpriteAnimationMap.SpriteAnimation>> animationMap = new LinkedHashMap<Direction, Map<Action, SpriteAnimationMap.SpriteAnimation>>();
    for(Spr spr : sprs) {
      SpriteAnimationMap.SpriteAnimation animation = new SpriteAnimationMap.SpriteAnimation();
      int length = spr.getLength();
      animation.setLength(length);
      animation.setDuration(spr.getDuration());
      Spr.SprFrame[] frames = spr.getFrames();
      OffsetImage[] images = new OffsetImage[length];
      for(int i = 0; i < length; i++) {
        Adrn adrn = rawModelService.getAdrn(frames[i].getImage());
        images[i] = offsetImageMapper.map(adrn);
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
    ret.setId(source.getId());
    ret.setAnimationMap(animationMap);

    return ret;
  }
}
