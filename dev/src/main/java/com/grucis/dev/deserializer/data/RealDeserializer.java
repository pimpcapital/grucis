package com.grucis.dev.deserializer.data;

import java.io.InputStream;
import java.io.RandomAccessFile;

import com.grucis.dev.io.ResourceAllocator;
import com.grucis.dev.model.raw.Adrn;
import com.grucis.dev.model.raw.Real;
import com.grucis.dev.utils.bitwise.BitwiseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class RealDeserializer extends DataModelDeserializer<Real, Adrn> {

  private static final Logger LOG = LoggerFactory.getLogger(RealDeserializer.class);

  @Autowired
  private ResourceAllocator resourceAllocator;

  public RealDeserializer() {
    super(DataType.REAL);
  }

  @Override
  protected Real deserialize(Adrn index) throws Exception {
    RandomAccessFile access = resourceAllocator.getReal();
    if(access == null) {
      LOG.error("REAL random access is not available");
      return null;
    }

    access.seek(index.getAddress());
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
    if(width != index.getWidth()) {
      LOG.warn("Image {} width {} does not match with index width {}", index.getId(), width, index.getWidth());
      width = index.getWidth();
    }
    ret.setWidth(width);

    byte[] heightBytes = new byte[4];
    access.read(heightBytes);
    int height = BitwiseUtils.int32(heightBytes);
    if(height != index.getHeight()) {
      LOG.warn("Image {} height {} does not match with index height {}", index.getId(), height, index.getHeight());
      height = index.getHeight();
    }
    ret.setHeight(height);

    byte[] sizeBytes = new byte[4];
    access.read(sizeBytes);
    int size = BitwiseUtils.int32(sizeBytes);
    if(size != index.getSize()) {
      LOG.warn("Image {} data block size {} does not match with index data block size {}", index.getId(), size, index.getSize());
      size = index.getSize();
    }
    ret.setSize(size);

    int copy = size - 16;
    byte[] dataBytes = new byte[copy];
    access.read(dataBytes);
    ret.setData(dataBytes);

    return ret;
  }
}
