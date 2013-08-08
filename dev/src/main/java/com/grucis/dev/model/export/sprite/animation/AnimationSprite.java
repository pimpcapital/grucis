package com.grucis.dev.model.export.sprite.animation;

import com.grucis.dev.model.export.sprite.SpriteSheet;

public final class AnimationSprite extends SpriteSheet<AnimationIndex> {

  public static final String NAME = "animation";

  public AnimationSprite(int id) {
    super(NAME, id);
  }
}
