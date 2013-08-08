package com.grucis.dev.model.export;

import com.grucis.dev.model.Model;

public abstract class ExportModel extends Model {

  private final String name;
  private final int id;

  protected ExportModel(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

}
