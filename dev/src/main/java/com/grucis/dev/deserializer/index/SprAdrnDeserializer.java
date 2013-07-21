package com.grucis.dev.deserializer.index;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.grucis.dev.model.raw.SprAdrn;

public final class SprAdrnDeserializer extends IndexModelDeserializer<SprAdrn> {
  public SprAdrnDeserializer(InputStream in) {
    super(in, IndexType.SPR_ADRN);
  }

  @Override
  protected Collection<SprAdrn> deserialize(InputStream in) throws Exception {
    Collection<SprAdrn> ret = new ArrayList<SprAdrn>();
    return ret;
  }
}
