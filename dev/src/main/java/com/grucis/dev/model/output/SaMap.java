package com.grucis.dev.model.output;

public final class SaMap extends OutputModel {

  private int south;
  private int east;
  private String name;
  private OffsetImage[][] tiles;
  private OffsetImage[][] objects;

  public SaMap(int id) {
    super(id);
  }

  public int getSouth() {
    return south;
  }

  public void setSouth(int south) {
    this.south = south;
  }

  public int getEast() {
    return east;
  }

  public void setEast(int east) {
    this.east = east;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OffsetImage[][] getTiles() {
    return tiles;
  }

  public void setTiles(OffsetImage[][] tiles) {
    this.tiles = tiles;
  }

  public OffsetImage[][] getObjects() {
    return objects;
  }

  public void setObjects(OffsetImage[][] objects) {
    this.objects = objects;
  }
}
