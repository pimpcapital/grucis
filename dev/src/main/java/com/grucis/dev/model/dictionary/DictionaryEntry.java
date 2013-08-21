package com.grucis.dev.model.dictionary;

import com.grucis.dev.model.Model;

public abstract class DictionaryEntry extends Model {
  private final int id;
  private final String path;

  protected DictionaryEntry(int id, String path) {
    this.id = id;
    this.path = path;
  }

  public int getId() {
    return id;
  }

  public String getPath() {
    return path;
  }
}
