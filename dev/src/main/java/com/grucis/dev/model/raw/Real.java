package com.grucis.dev.model.raw;

public final class Real extends RawModel {
  private String magic;
  private int compress;
  private int unknown;
  private int width;
  private int height;
  private int size;
  private int compressedData;

  public String getMagic() {
    return magic;
  }

  public void setMagic(String magic) {
    this.magic = magic;
  }

  public int getCompress() {
    return compress;
  }

  public void setCompress(int compress) {
    this.compress = compress;
  }

  public int getUnknown() {
    return unknown;
  }

  public void setUnknown(int unknown) {
    this.unknown = unknown;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getCompressedData() {
    return compressedData;
  }

  public void setCompressedData(int compressedData) {
    this.compressedData = compressedData;
  }
}
