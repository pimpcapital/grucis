package com.grucis.dev.model.export.sprite.map;

import com.grucis.dev.model.export.sprite.SpriteSheet;

public final class MapSprite extends SpriteSheet<MapIndex> {

  public static final String NAME = "map";

  public MapSprite() {
    super(NAME, MapIndex.class);
  }
}
