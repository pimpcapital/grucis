package com.grucis.dev.deserializer.data;

import java.io.InputStream;

import com.grucis.dev.model.raw.Spr;
import com.grucis.dev.model.raw.SprAdrn;

public final class SprDeserializer extends DataModelDeserializer<Spr, SprAdrn> {
  public SprDeserializer(InputStream in) {
    super(in, DataType.SPR);
  }

  @Override
  protected Spr deserialize(InputStream in, SprAdrn index) throws Exception {
    return null;
  }
}
