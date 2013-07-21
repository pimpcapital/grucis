package com.grucis.dev.mapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.grucis.dev.model.raw.SprAdrn;

public final class SprAdrnMapper extends Mapper<SprAdrn> {
  public SprAdrnMapper(InputStream in) {
    super(in);
  }

  @Override
  protected Collection<SprAdrn> generateModels(InputStream in) throws Exception {
    Collection<SprAdrn> ret = new ArrayList<SprAdrn>();
    return ret;
  }
}
