package com.grucis.dev.model.output;

import com.grucis.dev.model.Model;

public abstract class OutputModel extends Model {

  private final int id;

  protected OutputModel(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) return true;
    if(!(o instanceof OutputModel)) return false;
    OutputModel that = (OutputModel)o;
    return id == that.id;

  }

  @Override
  public int hashCode() {
    return id;
  }
}
