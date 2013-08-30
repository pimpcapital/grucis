package com.grucis.dev.model.export.map;

import java.util.Set;

import com.grucis.dev.model.export.ExportModel;

public final class TileMap extends ExportModel {

  private String name;
  private int east;
  private int south;
  private int[] tiles;
  private int[] objects;
  private Set<Integer> requires;

  public TileMap(int id) {
    super(id);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public Set<Integer> getRequires() {
    return requires;
  }

  public void setRequires(Set<Integer> requires) {
    this.requires = requires;
  }
}
