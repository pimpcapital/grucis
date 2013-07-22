package com.grucis.dev.deserializer.data;

import java.io.InputStream;

import com.grucis.dev.model.raw.Adrn;
import com.grucis.dev.model.raw.Real;
import com.grucis.dev.utils.bitwise.BitwiseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RealDeserializer extends DataModelDeserializer<Real, Adrn> {

  private static final Logger LOG = LoggerFactory.getLogger(RealDeserializer.class);

  public RealDeserializer(InputStream in) {
    super(in, DataType.REAL);
  }

  @Override
  protected Real deserialize(InputStream in, Adrn index) throws Exception {
    int readPos = index.getAddress();
    Real ret = new Real();

    byte[] magicBytes = new byte[2];
    in.read(magicBytes, readPos, 2);
    ret.setMagic(new String(magicBytes));
    readPos += 2;

    byte[] majorByte = new byte[1];
    in.read(majorByte, readPos, 1);
    ret.setMajor(BitwiseUtils.uint8(majorByte[0]));
    readPos++;

    byte[] minorByte = new byte[1];
    in.read(minorByte, readPos, 1);
    ret.setMinor(BitwiseUtils.uint8(minorByte[0]));
    readPos++;

    byte[] widthBytes = new byte[4];
    in.read(widthBytes, readPos, 4);
    int width = BitwiseUtils.int32(widthBytes);
    if(width != index.getWidth()) {
      LOG.warn("Image {} width {} does not match with index width {}", index.getId(), width, index.getWidth());
      width = index.getWidth();
    }
    ret.setWidth(width);
    readPos += 4;

    byte[] heightBytes = new byte[4];
    in.read(heightBytes, readPos, 4);
    int height = BitwiseUtils.int32(heightBytes);
    if(height != index.getHeight()) {
      LOG.warn("Image {} height {} does not match with index height {}", index.getId(), height, index.getHeight());
      height = index.getHeight();
    }
    ret.setHeight(height);
    readPos += 4;

    byte[] sizeBytes = new byte[4];
    in.read(sizeBytes, readPos, 4);
    int size = BitwiseUtils.int32(sizeBytes);
    if(size != index.getSize()) {
      LOG.warn("Image {} data block size {} does not match with index data block size {}", index.getId(), size, index.getSize());
      size = index.getSize();
    }
    ret.setSize(size);
    readPos += 4;

    int copy = size - 16;
    byte[] dataBytes = new byte[copy];
    in.read(dataBytes, readPos, copy);
    ret.setData(dataBytes);

    return ret;
  }
}
