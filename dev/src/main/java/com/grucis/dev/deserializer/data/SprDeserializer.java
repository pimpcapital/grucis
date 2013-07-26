package com.grucis.dev.deserializer.data;

import java.io.InputStream;

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
    InputStream in = resourceAllocator.getSpr();
    if(in == null) {
      LOG.error("SPR input stream is not available");
      return null;
    }

    int readPos = index.getAddress();
    Spr ret = new Spr();

    byte[] directionBytes = new byte[2];
    in.read(directionBytes, readPos, 2);
    ret.setDirection(BitwiseUtils.uint16(directionBytes));
    readPos += 2;

    byte[] actionBytes = new byte[2];
    in.read(actionBytes, readPos, 2);
    ret.setAction(BitwiseUtils.uint16(directionBytes));
    readPos += 2;

    byte[] durationBytes = new byte[4];
    in.read(durationBytes, readPos, 4);
    ret.setDuration(BitwiseUtils.uint32(durationBytes));
    readPos += 4;

    byte[] lengthBytes = new byte[4];
    in.read(lengthBytes, readPos, 4);
    int length = BitwiseUtils.uint32(lengthBytes);
    ret.setLength(length);
    readPos += 4;

    Spr.SprFrame[] frames = new Spr.SprFrame[length];
    for(int i = 0; i < length; i++) {
      Spr.SprFrame frame = new Spr.SprFrame();

      byte[] imageBytes = new byte[4];
      in.read(imageBytes, readPos, 4);
      frame.setImage(BitwiseUtils.uint32(imageBytes));
      readPos += 4;

      byte[] referenceBytes = new byte[6];
      in.read(referenceBytes, readPos, 6);
      frame.setReference(new String(referenceBytes));
      readPos += 6;

      frames[i] = frame;
    }
    ret.setFrames(frames);

    return ret;
  }
}
