package com.grucis.dev.mapper.export;

import com.grucis.dev.mapper.ModelMapper;
import com.grucis.dev.model.export.ExportModel;
import com.grucis.dev.model.output.OutputModel;

public abstract class ExportModelMapper<OM extends OutputModel, XM extends ExportModel> extends ModelMapper<OM, XM>  {
}
