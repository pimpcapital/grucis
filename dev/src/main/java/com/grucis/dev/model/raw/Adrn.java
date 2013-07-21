package com.grucis.dev.model.raw;

public final class Adrn extends RawModel{
  private int imageId;
  private int addressInReal;
  private int blockSize;
  private int xOffset;
  private int yOffset;
  private int width;
  private int height;
  private int east;
  private int south;
  private int sign;
  private String reference;
  private int mapElementId;

  public int getImageId() {
    return imageId;
  }

  public void setImageId(int imageId) {
    this.imageId = imageId;
  }

  public int getAddressInReal() {
    return addressInReal;
  }

  public void setAddressInReal(int addressInReal) {
    this.addressInReal = addressInReal;
  }

  public int getBlockSize() {
    return blockSize;
  }

  public void setBlockSize(int blockSize) {
    this.blockSize = blockSize;
  }

  public int getxOffset() {
    return xOffset;
  }

  public void setxOffset(int xOffset) {
    this.xOffset = xOffset;
  }

  public int getyOffset() {
    return yOffset;
  }

  public void setyOffset(int yOffset) {
    this.yOffset = yOffset;
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

  public int getEast() {
    return east;
  }

  public void setEast(int east) {
    this.east = east;
  }

  public int getSouth() {
    return south;
  }

  public void setSouth(int south) {
    this.south = south;
  }

  public int getSign() {
    return sign;
  }

  public void setSign(int sign) {
    this.sign = sign;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public int getMapElementId() {
    return mapElementId;
  }

  public void setMapElementId(int mapElementId) {
    this.mapElementId = mapElementId;
  }
}
