package com.grucis.dev.service;

import java.util.Map;
import java.util.TreeMap;

import com.grucis.dev.mapper.export.SpriteSheetMapper;
import com.grucis.dev.model.export.sprite.animation.AnimationSprite;
import com.grucis.dev.model.output.SpriteAnimationMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class SpriteSheetService {

  @Autowired
  private OutputModelService outputModelService;
  @Autowired
  private SpriteSheetMapper spriteSheetMapper;

  private Map<Integer, AnimationSprite> cache = new TreeMap<Integer, AnimationSprite>();

  public AnimationSprite getSpriteSheet(int id, boolean cacheResult) {
    AnimationSprite ret = cache.get(id);
    if(ret == null) {
      SpriteAnimationMap animationMap = outputModelService.getAnimation(id);
      ret = spriteSheetMapper.map(animationMap);
      if(cacheResult) cache.put(id, ret);
    }
    return ret;
  }
}
