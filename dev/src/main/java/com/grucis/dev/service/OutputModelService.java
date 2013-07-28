package com.grucis.dev.service;

import com.grucis.dev.mapper.output.OffsetImageMapper;
import com.grucis.dev.mapper.output.SpriteAnimationMapper;
import com.grucis.dev.model.output.OffsetImage;
import com.grucis.dev.model.output.SpriteAnimationMap;
import com.grucis.dev.model.raw.Adrn;
import com.grucis.dev.model.raw.SprAdrn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class OutputModelService {

  @Autowired
  private RawModelService rawModelService;
  @Autowired
  private OffsetImageMapper offsetImageMapper;
  @Autowired
  private SpriteAnimationMapper spriteAnimationMapper;

  public OffsetImage getImage(int id) {
    Adrn adrn = rawModelService.getAdrn(id);
    return offsetImageMapper.map(adrn);
  }

  public SpriteAnimationMap getAnimation(int id) {
    SprAdrn sprAdrn = rawModelService.getSprAdrn(id);
    return spriteAnimationMapper.map(sprAdrn);
  }
}
