package com.grucis.dev.mapper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.annotation.PostConstruct;

import com.grucis.dev.deserializer.data.RealDeserializer;
import com.grucis.dev.model.raw.Adrn;
import com.grucis.dev.model.raw.Real;
import com.grucis.dev.model.wrapped.OffsetImage;
import com.grucis.dev.service.RawModelService;
import com.grucis.dev.utils.bitwise.BitwiseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class OffsetImageMapper extends ModelMapper<Adrn, OffsetImage> {

  @Autowired
  private RawModelService rawModelService;

  Color[] palette;

  @PostConstruct
  public void init() throws Exception {
    InputStream paletteInput = getClass().getResourceAsStream("palette.dat");
    byte[] paletteBytes = new byte[1024];
    paletteInput.read(paletteBytes);

    palette = new Color[256];
    for(int i = 0; i < 256; i++) {
      int pos = i * 4;
      palette[i] = new Color(BitwiseUtils.uint8(paletteBytes[pos++]),
                              BitwiseUtils.uint8(paletteBytes[pos++]),
                              BitwiseUtils.uint8(paletteBytes[pos]));
    }
  }

  private BufferedImage generateImage(byte[] bitmap, int width, int height) {
    BufferedImage ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);



    return ret;
  }

  @Override
  public OffsetImage map(Adrn raw) {
    OffsetImage ret = new OffsetImage();

    ret.setxOffset(raw.getxOffset());
    ret.setyOffset(raw.getyOffset());

    RealDeserializer rd = new RealDeserializer(rawModelService.getRealInputStream());
    Real real = rd.getRawModel(raw);



    return null;
  }
}
