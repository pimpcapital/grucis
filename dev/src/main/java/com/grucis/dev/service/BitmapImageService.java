package com.grucis.dev.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BitmapImageService {

  private static final Logger LOG = LoggerFactory.getLogger(BitmapImageService.class);

  @Autowired
  private OutputModelService outputModelService;

  public byte[] getImageBytes(int id) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      ImageIO.write(outputModelService.getImage(id).getImage(), "png", out);
      return out.toByteArray();
    } catch(IOException e) {
      LOG.error("Cannot convert image {} into byte array", id);
      return null;
    }
  }
}
