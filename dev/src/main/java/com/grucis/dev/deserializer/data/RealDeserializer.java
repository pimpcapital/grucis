package com.grucis.dev.deserializer.data;

import java.io.InputStream;

import com.grucis.dev.model.raw.Adrn;
import com.grucis.dev.model.raw.Real;

public final class RealDeserializer extends DataModelDeserializer<Real, Adrn> {
  public RealDeserializer(InputStream in) {
    super(in, DataType.REAL);
  }

  @Override
  protected Real deserialize(InputStream in, Adrn index) throws Exception {
    int readPos = index.getAddress();
    int readMax = index.getSize() + readPos;
    return null;
  }
}
