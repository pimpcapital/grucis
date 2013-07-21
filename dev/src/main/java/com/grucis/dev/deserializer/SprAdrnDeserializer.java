package com.grucis.dev.deserializer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.grucis.dev.model.raw.SprAdrn;

public final class SprAdrnDeserializer extends Deserializer<SprAdrn> {
  public SprAdrnDeserializer(InputStream in) {
    super(in, DataType.SPR_ADRN);
  }

  @Override
  protected Collection<SprAdrn> deserialize(InputStream in) throws Exception {
    Collection<SprAdrn> ret = new ArrayList<SprAdrn>();
    return ret;
  }
}
