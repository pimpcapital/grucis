package com.grucis.dev.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.grucis.dev.model.output.OffsetImage;
import com.grucis.dev.utils.image.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BitmapImageService {

  private static final Logger LOG = LoggerFactory.getLogger(BitmapImageService.class);

  @Autowired
  private OutputModelService outputModelService;

  public BufferedImage getBitmapImage(int id) {
    return outputModelService.getImage(id).getImage();
  }
}
