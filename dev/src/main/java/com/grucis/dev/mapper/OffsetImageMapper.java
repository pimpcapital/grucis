package com.grucis.dev.mapper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.annotation.PostConstruct;

import com.grucis.dev.model.output.OffsetImage;
import com.grucis.dev.model.raw.Adrn;
import com.grucis.dev.model.raw.Real;
import com.grucis.dev.service.RawModelService;
import com.grucis.dev.utils.bitwise.BitwiseUtils;
import com.grucis.dev.utils.bitwise.UInt8;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class OffsetImageMapper extends ModelMapper<Adrn, OffsetImage> {

  @Autowired
  private RawModelService rawModelService;

  int[] palette;

  @PostConstruct
  public void init() throws Exception {
    InputStream paletteInput = getClass().getResourceAsStream("/palette.dat");
    byte[] paletteBytes = new byte[1024];
    paletteInput.read(paletteBytes);

    palette = new int[256];
    for(int i = 0; i < 256; i++) {
      int pos = i * 4;
      palette[i] = new Color(BitwiseUtils.uint8(paletteBytes[pos++]),
                              BitwiseUtils.uint8(paletteBytes[pos++]),
                              BitwiseUtils.uint8(paletteBytes[pos])).getRGB();
    }
  }

  private BufferedImage generateImage(byte[] bitmap, int width, int height) {
    BufferedImage ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    int x = 0;
    int y = height - 1;
    int pos = 0;
    int bitmapLength = bitmap.length;
    int pix;
    while(pos < bitmapLength) {
      pix =  new UInt8(bitmap[pos]).intValue();
      if(pix != 0) {
        ret.setRGB(x, y, palette[pix]);
      }
      x++;
      pos++;
      if(x == width) {
        x = 0;
        y--;
      }
    }
    return ret;
  }

  private byte[] extractBitmap(Real real) {
    int max = real.getWidth() * real.getHeight();
    byte[] ret = new byte[max];
    byte[] data = real.getData();
    int toRead = real.getSize() - 16;
    int readPos = 0;
    int writePos = 0;
    while(readPos < toRead) {
      int head = new UInt8(data[readPos++]).intValue();
      byte value = 0;
      boolean copy;
      int x, y, z;
      if(head >= 224) {
        copy = false;
        value = new UInt8(0).byteValue();
        x = head - 224;
        y = new UInt8(data[readPos++]).intValue();
        z = new UInt8(data[readPos++]).intValue();
      } else if(head >= 208) {
        copy = false;
        value = new UInt8(0).byteValue();
        x = 0;
        y = head - 208;
        z = new UInt8(data[readPos++]).intValue();
      } else if(head >= 192) {
        copy = false;
        value = new UInt8(0).byteValue();
        x = 0;
        y = 0;
        z = head - 192;
      } else if(head >= 160) {
        copy = false;
        value = data[readPos++];
        x = head - 160;
        y = new UInt8(data[readPos++]).intValue();
        z = new UInt8(data[readPos++]).intValue();
      } else if(head >= 144) {
        copy = false;
        value = data[readPos++];
        x = 0;
        y = head - 144;
        z = new UInt8(data[readPos++]).intValue();
      } else if(head >= 128) {
        copy = false;
        value = data[readPos++];
        x = 0;
        y = 0;
        z = head - 128;
      } else if(head >= 32) {
        copy = true;
        x = head - 32;
        y = new UInt8(data[readPos++]).intValue();
        z = new UInt8(data[readPos++]).intValue();
      } else if(head >= 16) {
        copy = true;
        x = 0;
        y = head - 16;
        z = new UInt8(data[readPos++]).intValue();
      } else {
        copy = true;
        x = 0;
        y = 0;
        z = head;
      }
      int total = x * 65536 + y * 256 + z;
      int canWrite = max - writePos;
      if(total > canWrite) {
        total = canWrite;
      }
      if(copy) {
        int canRead = toRead - readPos;
        if(total > canRead) {
          total = canRead;
        }
        for(int i = 0; i < total; i++) {
          value = data[readPos++];
          ret[writePos++] = value;
        }
      } else {
        for(int i = 0; i < total; i++) {
          ret[writePos++] = value;
        }
      }
    }
    return ret;
  }

  @Override
  public OffsetImage map(Adrn raw) {
    OffsetImage ret = new OffsetImage();

    ret.setxOffset(raw.getxOffset());
    ret.setyOffset(raw.getyOffset());

    Real real = rawModelService.getReal(raw);
    byte[] bitmap = extractBitmap(real);
    BufferedImage image = generateImage(bitmap, raw.getWidth(), raw.getHeight());
    ret.setImage(image);

    return ret;
  }
}
