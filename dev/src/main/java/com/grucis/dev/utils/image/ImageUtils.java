package com.grucis.dev.utils.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtils {

  private static final Logger LOG = LoggerFactory.getLogger(ImageUtils.class);


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
