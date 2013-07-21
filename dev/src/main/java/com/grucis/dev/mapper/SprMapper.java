package com.grucis.dev.mapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.grucis.dev.model.raw.Spr;

public final class SprMapper extends Mapper<Spr> {
  public SprMapper(InputStream in) {
    super(in);
  }

  @Override
  protected Collection<Spr> generateModels(InputStream in) throws Exception {
    Collection<Spr> ret = new ArrayList<Spr>();
    return ret;
  }
}
