package com.grucis.dev.mapper;

import com.grucis.dev.model.raw.RawModel;
import com.grucis.dev.model.wrapped.WrappedModel;

public abstract class ModelMapper<RM extends RawModel, WM extends WrappedModel> {
  public abstract WM map(RM raw);
}
