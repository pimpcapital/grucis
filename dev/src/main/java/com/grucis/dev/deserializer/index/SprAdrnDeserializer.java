package com.grucis.dev.deserializer.index;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.grucis.dev.model.raw.SprAdrn;
import com.grucis.dev.utils.bitwise.BitwiseUtils;

public final class SprAdrnDeserializer extends IndexModelDeserializer<SprAdrn> {
  public SprAdrnDeserializer(InputStream in) {
    super(in, IndexType.SPR_ADRN);
  }

  @Override
  protected Collection<SprAdrn> deserialize(InputStream in) throws Exception {
    Collection<SprAdrn> ret = new ArrayList<SprAdrn>();
    int readPos = 0;
    int readMax = in.available();
    while(readPos < readMax) {
      SprAdrn sprAdrn = new SprAdrn();

      byte[] idBytes = new byte[4];
      in.read(idBytes, readPos, 4);
      sprAdrn.setId(BitwiseUtils.uint32(idBytes));
      readPos += 4;

      byte[] addressBytes = new byte[4];
      in.read(addressBytes, readPos, 4);
      sprAdrn.setAddress(BitwiseUtils.uint32(addressBytes));
      readPos += 4;

      byte[] actionsBytes = new byte[2];
      in.read(actionsBytes, readPos, 2);
      sprAdrn.setAddress(BitwiseUtils.uint16(actionsBytes));
      readPos += 2;

      byte[] soundBytes = new byte[2];
      in.read(soundBytes, readPos, 2);
      sprAdrn.setSound(BitwiseUtils.uint16(soundBytes));
      readPos += 2;

      ret.add(sprAdrn);
    }

    return ret;
  }
}
