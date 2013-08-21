package com.grucis.dev.model.raw.atomic;

import com.grucis.dev.model.raw.RawModel;

public abstract class AtomicModel extends RawModel {

  private final String path;

  protected AtomicModel(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }
}
