package com.grucis.dev.deserializer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.grucis.dev.model.raw.Spr;

public final class SprDeserializer extends Deserializer<Spr> {
  public SprDeserializer(InputStream in) {
    super(in, DataType.SPR);
  }

  @Override
  protected Collection<Spr> deserialize(InputStream in) throws Exception {
    Collection<Spr> ret = new ArrayList<Spr>();
    return ret;
  }
}
