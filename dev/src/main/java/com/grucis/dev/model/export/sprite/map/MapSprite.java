package com.grucis.dev.model.export.sprite.map;

import com.grucis.dev.model.export.sprite.SpriteSheet;

public final class MapSprite extends SpriteSheet<MapIndex> {

  public static final String NAME = "map";

  public MapSprite(int id) {
    super(NAME, id);
  }
}
