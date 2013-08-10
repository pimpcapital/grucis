package com.grucis.dev.model.export;

import com.grucis.dev.model.Model;

public abstract class ExportModel extends Model {
  private final int id;

  protected ExportModel(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

}
