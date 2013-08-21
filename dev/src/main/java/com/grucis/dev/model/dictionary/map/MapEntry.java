package com.grucis.dev.model.dictionary.map;

import com.grucis.dev.model.dictionary.DictionaryEntry;

public final class MapEntry extends DictionaryEntry {
  private int south;
  private int east;
  private String name;

  public MapEntry(int id, String ref) {
    super(id, ref);
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
}
