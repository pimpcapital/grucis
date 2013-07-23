package com.grucis.dev.deserializer.index;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.grucis.dev.io.ResourceAllocator;
import com.grucis.dev.model.raw.SprAdrn;
import com.grucis.dev.utils.bitwise.BitwiseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class SprAdrnDeserializer extends IndexModelDeserializer<SprAdrn> {

  @Autowired
  private ResourceAllocator resourceAllocator;

  public SprAdrnDeserializer() {
    super(IndexType.SPR_ADRN);
  }

  @Override
  protected Collection<SprAdrn> deserialize() throws Exception {
    Collection<SprAdrn> ret = new ArrayList<SprAdrn>();
    InputStream in = resourceAllocator.getSprAdrn();

    while(in.available() > 0) {
      SprAdrn sprAdrn = new SprAdrn();

      byte[] idBytes = new byte[4];
      in.read(idBytes);
      sprAdrn.setId(BitwiseUtils.uint32(idBytes));

      byte[] addressBytes = new byte[4];
      in.read(addressBytes);
      sprAdrn.setAddress(BitwiseUtils.uint32(addressBytes));

      byte[] actionsBytes = new byte[2];
      in.read(actionsBytes);
      sprAdrn.setAddress(BitwiseUtils.uint16(actionsBytes));

      byte[] soundBytes = new byte[2];
      in.read(soundBytes);
      sprAdrn.setSound(BitwiseUtils.uint16(soundBytes));

      ret.add(sprAdrn);
    }

    return ret;
  }
}
