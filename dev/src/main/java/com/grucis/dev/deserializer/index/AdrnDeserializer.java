package com.grucis.dev.deserializer.index;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.grucis.dev.model.raw.Adrn;
import com.grucis.dev.utils.bitwise.BitwiseUtils;

public final class AdrnDeserializer extends IndexModelDeserializer<Adrn> {
  public AdrnDeserializer(InputStream in) {
    super(in, IndexType.ADRN);
  }

  @Override
  protected Collection<Adrn> deserialize(InputStream in) throws Exception {
    Collection<Adrn> ret = new ArrayList<Adrn>();
    int readPos = 0;
    int readMax = in.available();
    while(readPos < readMax) {
      Adrn adrn = new Adrn();

      byte[] idBytes = new byte[4];
      in.read(idBytes, readPos, 4);
      adrn.setId(BitwiseUtils.int32(idBytes));
      readPos += 4;

      byte[] addressBytes = new byte[4];
      in.read(addressBytes, readPos, 4);
      adrn.setAddress(BitwiseUtils.uint32(addressBytes));
      readPos += 4;

      byte[] sizeBytes = new byte[4];
      in.read(sizeBytes, readPos, 4);
      adrn.setSize(BitwiseUtils.uint32(sizeBytes));
      readPos += 4;

      byte[] xOffsetBytes = new byte[4];
      in.read(xOffsetBytes, readPos, 4);
      adrn.setxOffset(BitwiseUtils.int32(xOffsetBytes));
      readPos += 4;

      byte[] yOffsetBytes = new byte[4];
      in.read(yOffsetBytes, readPos, 4);
      adrn.setyOffset(BitwiseUtils.int32(yOffsetBytes));
      readPos += 4;

      byte[] widthBytes = new byte[4];
      in.read(widthBytes, readPos, 4);
      adrn.setWidth(BitwiseUtils.int32(widthBytes));
      readPos += 4;

      byte[] heightBytes = new byte[4];
      in.read(heightBytes, readPos, 4);
      adrn.setHeight(BitwiseUtils.int32(heightBytes));
      readPos += 4;

      byte[] eastByte = new byte[1];
      in.read(eastByte, readPos, 1);
      adrn.setEast(BitwiseUtils.uint8(eastByte[0]));
      readPos++;

      byte[] southByte = new byte[1];
      in.read(southByte, readPos, 1);
      adrn.setSouth(BitwiseUtils.uint8(southByte[0]));
      readPos++;

      byte[] referenceBytes = new byte[45];
      in.read(referenceBytes, readPos, 45);
      adrn.setReference(new String(referenceBytes));
      readPos += 45;

      byte[] mapBytes = new byte[4];
      in.read(mapBytes, readPos, 4);
      adrn.setMap(BitwiseUtils.int32(mapBytes));
      readPos += 4;

      ret.add(adrn);
    }
    return ret;
  }
}
