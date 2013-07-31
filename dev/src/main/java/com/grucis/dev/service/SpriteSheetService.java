package com.grucis.dev.service;

import java.util.Map;
import java.util.TreeMap;

import com.grucis.dev.mapper.export.SpriteSheetMapper;
import com.grucis.dev.model.export.SpriteSheet;
import com.grucis.dev.model.output.SpriteAnimationMap;
import com.grucis.dev.utils.image.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class SpriteSheetService {

  @Autowired
  private OutputModelService outputModelService;
  @Autowired
  private SpriteSheetMapper spriteSheetMapper;

  private Map<Integer, SpriteSheet> cache = new TreeMap<Integer, SpriteSheet>();

  public SpriteSheet getSpriteSheet(int id, boolean cacheResult) {
    SpriteSheet ret = cache.get(id);
    if(ret == null) {
      SpriteAnimationMap animationMap = outputModelService.getAnimation(id);
      ret = spriteSheetMapper.map(animationMap);
      if(cacheResult) cache.put(id, ret);
    }
    return ret;
  }
}
