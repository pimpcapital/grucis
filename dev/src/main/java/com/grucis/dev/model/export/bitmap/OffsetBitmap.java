package com.grucis.dev.model.export.bitmap;

import java.awt.image.BufferedImage;

import com.grucis.dev.model.export.ExportModel;

public final class OffsetBitmap extends ExportModel {

  public static final String NAME = "bitmap";

  private BitmapIndex index;
  private BufferedImage image;

  public OffsetBitmap(int id) {
    super(NAME, id);
  }

  public BitmapIndex getIndex() {
    return index;
  }

  public void setIndex(BitmapIndex index) {
    this.index = index;
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }
}
