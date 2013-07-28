package com.grucis.dev.mapper.export;

import java.util.Map;

import com.grucis.dev.logic.SpriteSheetPacker;
import com.grucis.dev.model.export.SpriteSheet;
import com.grucis.dev.model.output.Action;
import com.grucis.dev.model.output.Direction;
import com.grucis.dev.model.output.SpriteAnimationMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class SpriteSheetMapper extends ExportModelMapper<SpriteAnimationMap, SpriteSheet> {

  @Autowired
  private SpriteSheetPacker spriteSheetPacker;

  @Override
  public SpriteSheet map(SpriteAnimationMap source) {
    SpriteSheet ret = new SpriteSheet();

    Map<Direction, Map<Action, SpriteAnimationMap.SpriteAnimation>> animationMap = source.getAnimationMap();

    return ret;
  }
}
