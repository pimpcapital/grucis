package com.grucis.dev.deserializer.data;

import java.io.InputStream;
import java.io.RandomAccessFile;

import com.grucis.dev.io.ResourceAllocator;
import com.grucis.dev.model.raw.Spr;
import com.grucis.dev.model.raw.SprAdrn;
import com.grucis.dev.utils.bitwise.BitwiseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class SprDeserializer extends DataModelDeserializer<Spr, SprAdrn> {

  private static final Logger LOG = LoggerFactory.getLogger(SprDeserializer.class);

  @Autowired
  private ResourceAllocator resourceAllocator;

  public SprDeserializer() {
    super(DataType.SPR);
  }

  @Override
  protected Spr deserialize(SprAdrn index) throws Exception {
    RandomAccessFile access = resourceAllocator.getSpr();
    if(access == null) {
      LOG.error("SPR random access is not available");
      return null;
    }

    access.seek(index.getAddress());
    Spr ret = new Spr();

    byte[] directionBytes = new byte[2];
    access.read(directionBytes);
    ret.setDirection(BitwiseUtils.uint16(directionBytes));

    byte[] actionBytes = new byte[2];
    access.read(actionBytes);
    ret.setAction(BitwiseUtils.uint16(directionBytes));

    byte[] durationBytes = new byte[4];
    access.read(durationBytes);
    ret.setDuration(BitwiseUtils.uint32(durationBytes));

    byte[] lengthBytes = new byte[4];
    access.read(lengthBytes);
    int length = BitwiseUtils.uint32(lengthBytes);
    ret.setLength(length);

    Spr.SprFrame[] frames = new Spr.SprFrame[length];
    for(int i = 0; i < length; i++) {
      Spr.SprFrame frame = new Spr.SprFrame();

      byte[] imageBytes = new byte[4];
      access.read(imageBytes);
      frame.setImage(BitwiseUtils.uint32(imageBytes));

      byte[] referenceBytes = new byte[6];
      access.read(referenceBytes);
      frame.setReference(new String(referenceBytes));

      frames[i] = frame;
    }
    ret.setFrames(frames);

    access.close();

    return ret;
  }
}
