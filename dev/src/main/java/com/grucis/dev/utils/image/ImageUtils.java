package com.grucis.dev.utils.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import com.grucis.dev.utils.bitwise.BitwiseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ImageUtils {

  private static final Logger LOG = LoggerFactory.getLogger(ImageUtils.class);

  public static int[] readPalette(InputStream input) {
    try {
      int size = input.available();
      if(size != 708) {
        LOG.error("This input stream does not provide a standard palette file");
        return null;
      }

      int[] ret = new int[256];
      ret[0] = new Color(0, 0, 0).getRGB();
      ret[1] = new Color(128, 0, 0).getRGB();
      ret[2] = new Color(0, 128, 0).getRGB();
      ret[3] = new Color(128, 128, 0).getRGB();
      ret[4] = new Color(0, 0, 128).getRGB();
      ret[5] = new Color(128, 0, 128).getRGB();
      ret[6] = new Color(0, 128, 128).getRGB();
      ret[7] = new Color(0 , 192, 192).getRGB();
      ret[8] = new Color(192, 220, 192).getRGB();
      ret[9] = new Color(166, 202, 240).getRGB();
      ret[10] = new Color(222, 0, 0).getRGB();
      ret[11] = new Color(255, 95, 0).getRGB();
      ret[12] = new Color(255, 255, 160).getRGB();
      ret[13] = new Color(0, 95, 210).getRGB();
      ret[14] = new Color(80, 210, 255).getRGB();
      ret[15] = new Color(40, 225, 40).getRGB();

      ret[240] = new Color(245, 195, 150).getRGB();
      ret[241] = new Color(30, 160, 95).getRGB();
      ret[242] = new Color(195, 125, 70).getRGB();
      ret[243] = new Color(155, 85, 30).getRGB();
      ret[244] = new Color(70, 65, 55).getRGB();
      ret[245] = new Color(40, 35, 30).getRGB();
      ret[246] = new Color(255, 251, 240).getRGB();
      ret[247] = new Color(58, 110, 165).getRGB();
      ret[248] = new Color(128, 128, 128).getRGB();
      ret[249] = new Color(255, 0, 0).getRGB();
      ret[250] = new Color(0, 255, 0).getRGB();
      ret[251] = new Color(255, 255, 0).getRGB();
      ret[252] = new Color(0, 0, 255).getRGB();
      ret[253] = new Color(255, 128, 255).getRGB();
      ret[254] = new Color(0, 255, 255).getRGB();
      ret[255] = new Color(255, 255, 255).getRGB();

      for(int i = 0; i < 224; i++) {
        int b = BitwiseUtils.uint8((byte) input.read());
        int g = BitwiseUtils.uint8((byte) input.read());
        int r = BitwiseUtils.uint8((byte) input.read());
        ret[i + 16] = new Color(r, g, b).getRGB();
      }
      input.close();

      return ret;
    } catch(IOException e) {
      LOG.error("Cannot read input stream");
      return null;
    }
  }

  public static byte[] toBytes(BufferedImage image) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      ImageIO.write(image, "png", out);
      return out.toByteArray();
    } catch(IOException e) {
      LOG.error("Cannot convert image into byte array", e);
      return null;
    }
  }
}
