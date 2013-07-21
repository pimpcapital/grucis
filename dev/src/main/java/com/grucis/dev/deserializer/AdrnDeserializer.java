package com.grucis.dev.deserializer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.grucis.dev.model.raw.Adrn;
import com.grucis.dev.utils.bitwise.BitwiseUtils;

public final class AdrnDeserializer extends Deserializer<Adrn> {
  public AdrnDeserializer(InputStream in) {
    super(in, DataType.ADRN);
  }

  @Override
  protected Collection<Adrn> deserialize(InputStream in) throws Exception {
    Collection<Adrn> ret = new ArrayList<Adrn>();
    int readPos = 0;
    int readMax = in.available();
    while(readPos < readMax) {
      Adrn adrn = new Adrn();

      byte[] imageIdBytes = new byte[4];
      in.read(imageIdBytes, readPos, 4);
      adrn.setImageId(BitwiseUtils.int32(imageIdBytes));
      readPos += 4;

      byte[] addressInReadBytes = new byte[4];
      in.read(addressInReadBytes, readPos, 4);
      adrn.setAddressInReal(BitwiseUtils.uint32(addressInReadBytes));
      readPos += 4;

      byte[] blockSizeBytes = new byte[4];
      in.read(blockSizeBytes, readPos, 4);
      adrn.setBlockSize(BitwiseUtils.uint32(blockSizeBytes));
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

      byte[] mapElementIdBytes = new byte[4];
      in.read(mapElementIdBytes, readPos, 4);
      adrn.setMapElementId(BitwiseUtils.int32(mapElementIdBytes));
      readPos += 4;

      ret.add(adrn);
    }
    return ret;
  }
}
