package com.grucis.dev.logic;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import com.grucis.dev.utils.math.IntegerUtils;
import org.springframework.stereotype.Component;

@Component
public final class ImagePacker {

  public Map<BufferedImage, Rectangle> packImageRectangles(List<BufferedImage> images, int padding, boolean requirePow2, boolean requireSquare, int outputWidth, int outputHeight) {
    Collections.sort(images, new Comparator<BufferedImage>() {
      @Override
      public int compare(BufferedImage i1, BufferedImage i2) {
        int c = Integer.compare(i2.getWidth(), i1.getWidth());
        if(c != 0) return c;
        return Integer.compare(i2.getHeight(), i1.getHeight());
      }
    });

    Map<BufferedImage, Rectangle> testImagePlacement = new HashMap<BufferedImage, Rectangle>();

    int smallestWidth = Integer.MAX_VALUE;
    int smallestHeight = Integer.MAX_VALUE;
    for(BufferedImage image : images) {
      smallestWidth = Math.min(smallestWidth, image.getWidth());
      smallestHeight = Math.min(smallestHeight, image.getHeight());
    }

    int testWidth = outputWidth;
    int testHeight = outputHeight;
    boolean shrinkVertical = false;
    Map<BufferedImage, Rectangle> imagePlacement = new HashMap<BufferedImage, Rectangle>();
    while(true) {
      testImagePlacement.clear();

      if(!testPackingImages(images, padding, testWidth, testHeight, testImagePlacement)) {
        if(imagePlacement.size() == 0) return null;
        if(shrinkVertical) return imagePlacement;

        shrinkVertical = true;
        testWidth += smallestWidth + padding + padding;
        testHeight += smallestHeight + padding + padding;
        continue;
      }

      imagePlacement = new HashMap<BufferedImage, Rectangle>(testImagePlacement);

      testWidth = testHeight = 0;
      for(Map.Entry<BufferedImage, Rectangle> entry : imagePlacement.entrySet()) {
        testWidth = Math.max(testWidth, (int)entry.getValue().getMaxX());
        testHeight = Math.max(testHeight, (int)entry.getValue().getMaxY());
      }

      if(!shrinkVertical) testWidth -= padding;
      testHeight -= padding;

      if(requirePow2) {
        testWidth = IntegerUtils.findNextPowerOfTwo(testWidth);
        testHeight = IntegerUtils.findNextPowerOfTwo(testHeight);
      }

      if(requireSquare) {
        int max = Math.max(testWidth, testHeight);
        testWidth = testHeight = max;
      }

      if(testWidth == outputWidth && testHeight == outputHeight) {
        if(shrinkVertical) return imagePlacement;
        shrinkVertical = true;
      }

      outputWidth = testWidth;
      outputHeight = testHeight;

      if(!shrinkVertical) testWidth -= smallestWidth;
      testHeight -= smallestHeight;
    }
  }

  private boolean testPackingImages(List<BufferedImage> images, int padding, int testWidth, int testHeight, Map<BufferedImage, Rectangle> testImagePlacement) {
    ArevaloRectanglePacker rectanglePacker = new ArevaloRectanglePacker(testWidth, testHeight);

    for(BufferedImage image : images) {
      Point placement = rectanglePacker.tryPack(image.getWidth() + padding, image.getHeight() + padding);
      if(placement == null) return false;
      testImagePlacement.put(image, new Rectangle(placement.x, placement.y, image.getWidth() + padding, image.getHeight() + padding));
    }

    return true;
  }

  public BufferedImage generateOutputImage(Map<BufferedImage, Rectangle> placement, int width, int height) {
    return null;
  }
}
