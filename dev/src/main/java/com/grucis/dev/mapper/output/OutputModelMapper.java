package com.grucis.dev.mapper.output;

import com.grucis.dev.mapper.ModelMapper;
import com.grucis.dev.model.output.OutputModel;
import com.grucis.dev.model.raw.RawModel;

public abstract class OutputModelMapper<RM extends RawModel, OM extends OutputModel> extends ModelMapper<RM, OM> {
}
