package com.grucis.dev.deserializer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.grucis.dev.model.raw.Real;

public final class RealDeserializer extends Deserializer<Real> {
  public RealDeserializer(InputStream in) {
    super(in, DataType.REAL);
  }

  @Override
  protected Collection<Real> deserialize(InputStream in) throws Exception {
    Collection<Real> ret = new ArrayList<Real>();
    return ret;
  }
}
