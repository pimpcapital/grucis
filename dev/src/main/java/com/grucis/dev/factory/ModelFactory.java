package com.grucis.dev.factory;

import com.grucis.dev.model.raw.RawModel;
import com.grucis.dev.model.wrapped.WrappedModel;

public abstract class ModelFactory<RM extends RawModel, WM extends WrappedModel> {
  public abstract WM convert(RM raw);
}
