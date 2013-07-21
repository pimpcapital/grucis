package com.grucis.dev.mapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.grucis.dev.model.raw.Adrn;

public final class AdrnMapper extends Mapper<Adrn> {
  public AdrnMapper(InputStream in) {
    super(in);
  }

  @Override
  protected Collection<Adrn> generateModels(InputStream in) throws Exception {
    Collection<Adrn> ret = new ArrayList<Adrn>();
    return ret;
  }
}
