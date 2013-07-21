package com.grucis.dev.mapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.grucis.dev.model.raw.Real;

public final class RealMapper extends Mapper<Real> {
  public RealMapper(InputStream in) {
    super(in);
  }

  @Override
  protected Collection<Real> generateModels(InputStream in) throws Exception {
    Collection<Real> ret = new ArrayList<Real>();
    return ret;
  }
}
