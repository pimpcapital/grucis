package com.grucis.dev.deserializer.data;

import java.io.RandomAccessFile;

import com.grucis.dev.io.ResourceAllocator;
import com.grucis.dev.model.raw.data.Real;
import com.grucis.dev.utils.bitwise.BitwiseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class RealDeserializer extends DataModelDeserializer<Real> {

  private static final Logger LOG = LoggerFactory.getLogger(RealDeserializer.class);

  @Autowired
  private ResourceAllocator resourceAllocator;

  public RealDeserializer() {
    super(DataType.REAL);
  }

  @Override
  protected Real deserialize(int address) throws Exception {
    RandomAccessFile access = resourceAllocator.getReal();
    if(access == null) {
      LOG.error("REAL random access is not available");
      return null;
    }

    access.seek(address);
    Real ret = new Real();

    byte[] magicBytes = new byte[2];
    access.read(magicBytes);
    ret.setMagic(new String(magicBytes));

    byte majorByte = access.readByte();
    ret.setMajor(BitwiseUtils.uint8(majorByte));

    byte minorByte = access.readByte();
    ret.setMinor(BitwiseUtils.uint8(minorByte));

    byte[] widthBytes = new byte[4];
    access.read(widthBytes);
    int width = BitwiseUtils.int32(widthBytes);
    ret.setWidth(width);

    byte[] heightBytes = new byte[4];
    access.read(heightBytes);
    int height = BitwiseUtils.int32(heightBytes);
    ret.setHeight(height);

    byte[] sizeBytes = new byte[4];
    access.read(sizeBytes);
    int size = BitwiseUtils.int32(sizeBytes);
    ret.setSize(size);

    int copy = size - 16;
    byte[] dataBytes = new byte[copy];
    access.read(dataBytes);
    ret.setData(dataBytes);

    access.close();

    return ret;
  }

  @Override
  public int getDataSize(Real model) {
    return model.getSize();
  }
}
