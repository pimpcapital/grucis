package com.grucis.dev.mapper;

import com.grucis.dev.model.output.OutputModel;
import com.grucis.dev.model.raw.RawModel;

public abstract class ModelMapper<RM extends RawModel, OM extends OutputModel> {
  public abstract OM map(RM raw);
}
