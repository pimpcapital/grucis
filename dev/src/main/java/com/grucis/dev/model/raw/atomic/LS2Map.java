package com.grucis.dev.model.raw.atomic;

public final class LS2Map extends AtomicModel {
  private String version;
  private int east;
  private int south;
  private int id;
  private String name;
  private int[] tiles;
  private int[] objects;

  public LS2Map(String path) {
    super(path);
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
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

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int[] getTiles() {
    return tiles;
  }

  public void setTiles(int[] tiles) {
    this.tiles = tiles;
  }

  public int[] getObjects() {
    return objects;
  }

  public void setObjects(int[] objects) {
    this.objects = objects;
  }
}
