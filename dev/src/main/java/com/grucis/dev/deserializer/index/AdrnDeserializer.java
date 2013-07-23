package com.grucis.dev.deserializer.index;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.grucis.dev.io.ResourceAllocator;
import com.grucis.dev.model.raw.Adrn;
import com.grucis.dev.utils.bitwise.BitwiseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class AdrnDeserializer extends IndexModelDeserializer<Adrn> {

  @Autowired
  private ResourceAllocator resourceAllocator;

  public AdrnDeserializer() {
    super(IndexType.ADRN);
  }

  @Override
  protected Collection<Adrn> deserialize() throws Exception {
    Collection<Adrn> ret = new ArrayList<Adrn>();
    InputStream in = resourceAllocator.getAdrn();

    while(in.available() > 0) {
      Adrn adrn = new Adrn();

      byte[] idBytes = new byte[4];
      in.read(idBytes);
      adrn.setId(BitwiseUtils.int32(idBytes));

      byte[] addressBytes = new byte[4];
      in.read(addressBytes);
      adrn.setAddress(BitwiseUtils.uint32(addressBytes));

      byte[] sizeBytes = new byte[4];
      in.read(sizeBytes);
      adrn.setSize(BitwiseUtils.uint32(sizeBytes));

      byte[] xOffsetBytes = new byte[4];
      in.read(xOffsetBytes);
      adrn.setxOffset(BitwiseUtils.int32(xOffsetBytes));

      byte[] yOffsetBytes = new byte[4];
      in.read(yOffsetBytes);
      adrn.setyOffset(BitwiseUtils.int32(yOffsetBytes));

      byte[] widthBytes = new byte[4];
      in.read(widthBytes);
      adrn.setWidth(BitwiseUtils.int32(widthBytes));

      byte[] heightBytes = new byte[4];
      in.read(heightBytes);
      adrn.setHeight(BitwiseUtils.int32(heightBytes));

      byte[] eastByte = new byte[1];
      in.read(eastByte);
      adrn.setEast(BitwiseUtils.uint8(eastByte[0]));

      byte[] southByte = new byte[1];
      in.read(southByte);
      adrn.setSouth(BitwiseUtils.uint8(southByte[0]));

      byte[] pathByte = new byte[1];
      in.read(pathByte);
      adrn.setPath(BitwiseUtils.uint8(pathByte[0]));

      byte[] referenceBytes = new byte[45];
      in.read(referenceBytes);
      adrn.setReference(new String(referenceBytes));

      byte[] mapBytes = new byte[4];
      in.read(mapBytes);
      adrn.setMap(BitwiseUtils.int32(mapBytes));

      ret.add(adrn);
    }
    return ret;
  }
}
