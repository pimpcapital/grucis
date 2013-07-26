package com.grucis.dev.mapper;

import com.grucis.dev.model.output.OffsetImage;
import com.grucis.dev.model.output.SpriteAnimation;
import com.grucis.dev.model.raw.Adrn;
import com.grucis.dev.model.raw.Spr;
import com.grucis.dev.model.raw.SprAdrn;
import com.grucis.dev.service.RawModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class SpriteAnimationMapper extends ModelMapper<SprAdrn, SpriteAnimation> {

  @Autowired
  private RawModelService rawModelService;
  @Autowired
  private OffsetImageMapper offsetImageMapper;

  @Override
  public SpriteAnimation map(SprAdrn raw) {
    SpriteAnimation ret = new SpriteAnimation();

    Spr spr = rawModelService.getSpr(raw);

    ret.setDuration(spr.getDuration());
    int length = spr.getLength();
    ret.setLength(length);
    Spr.SprFrame[] frames = spr.getFrames();
    OffsetImage[] images = new OffsetImage[length];
    for(int i = 0; i < length; i++) {
      Adrn adrn = rawModelService.getAdrn(frames[i].getImage());
      images[i] = offsetImageMapper.map(adrn);
    }
    ret.setFrames(images);

    return ret;
  }
}
