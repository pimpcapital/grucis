package com.grucis.dev.model.export.map;

import java.awt.image.BufferedImage;

import com.grucis.dev.model.export.ExportModel;

public final class MapElementSpriteSheet extends ExportModel {

  private BufferedImage image;
  private MapElementSpriteSheetIndex index;

  public MapElementSpriteSheet(int id) {
    super(id);
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

  public MapElementSpriteSheetIndex getIndex() {
    return index;
  }

  public void setIndex(MapElementSpriteSheetIndex index) {
    this.index = index;
  }

}
