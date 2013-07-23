package com.grucis.dev.model.raw;

public final class Adrn extends RawModel{
  private int id;
  private int address;
  private int size;
  private int xOffset;
  private int yOffset;
  private int width;
  private int height;
  private int east;
  private int south;
  private int path;
  private String reference;
  private int map;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getAddress() {
    return address;
  }

  public void setAddress(int address) {
    this.address = address;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
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

  public int getPath() {
    return path;
  }

  public void setPath(int path) {
    this.path = path;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public int getMap() {
    return map;
  }

  public void setMap(int map) {
    this.map = map;
  }
}
