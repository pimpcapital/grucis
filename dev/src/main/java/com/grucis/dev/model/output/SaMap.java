package com.grucis.dev.model.output;

import java.util.TreeSet;

public final class SaMap extends OutputModel {

  private int south;
  private int east;
  private String name;
  private TreeSet<Integer> require;
  private int[][] tiles;
  private int[][] objects;

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

  public TreeSet<Integer> getRequire() {
    return require;
  }

  public void setRequire(TreeSet<Integer> require) {
    this.require = require;
  }

  public int[][] getTiles() {
    return tiles;
  }

  public void setTiles(int[][] tiles) {
    this.tiles = tiles;
  }

  public int[][] getObjects() {
    return objects;
  }

  public void setObjects(int[][] objects) {
    this.objects = objects;
  }
}
