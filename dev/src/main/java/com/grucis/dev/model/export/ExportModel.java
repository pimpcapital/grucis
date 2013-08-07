package com.grucis.dev.model.export;

import com.grucis.dev.model.Model;

public abstract class ExportModel extends Model {

  private final String name;
  protected int index;

  protected ExportModel(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
