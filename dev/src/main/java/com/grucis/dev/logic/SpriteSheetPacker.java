package com.grucis.dev.logic;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import com.grucis.dev.utils.math.IntegerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SpriteSheetPacker {

  private Logger LOG = LoggerFactory.getLogger(SpriteSheetPacker.class);

  private List<BufferedImage> images = new ArrayList<BufferedImage>();
  private Map<BufferedImage, Rectangle> imagePlacements;
  private BufferedImage spriteImage;
  private int padding = 0;
  private boolean requirePow2 = false;
  private boolean requireSquare = false;
  private int outputWidth = 1024;
  private int outputHeight = 4096;

  public List<BufferedImage> getImages() {
    return images;
  }

  public void setImages(List<BufferedImage> images) {
    this.images = images;
  }

  public Map<BufferedImage, Rectangle> getImagePlacements() {
    return imagePlacements;
  }

  public BufferedImage getSpriteImage() {
    return spriteImage;
  }

  public int getPadding() {
    return padding;
  }

  public void setPadding(int padding) {
    this.padding = padding;
  }

  public boolean isRequirePow2() {
    return requirePow2;
  }

  public void setRequirePow2(boolean requirePow2) {
    this.requirePow2 = requirePow2;
  }

  public boolean isRequireSquare() {
    return requireSquare;
  }

  public void setRequireSquare(boolean requireSquare) {
    this.requireSquare = requireSquare;
  }

  public int getOutputWidth() {
    return outputWidth;
  }

  public void setOutputWidth(int outputWidth) {
    this.outputWidth = outputWidth;
  }

  public int getOutputHeight() {
    return outputHeight;
  }

  public void setOutputHeight(int outputHeight) {
    this.outputHeight = outputHeight;
  }

  public void addImage(BufferedImage image) {
    images.add(image);
  }

  public void addImages(Collection<BufferedImage> images) {
    this.images.addAll(images);
  }

  public Map<BufferedImage, Rectangle> packImageRectangles() {
    if(images.isEmpty()) {
      LOG.error("There is no image to pack");
      return imagePlacements;
    }

    Collections.sort(images, new Comparator<BufferedImage>() {
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
    imagePlacements = new HashMap<BufferedImage, Rectangle>();
    while(true) {
      testImagePlacement.clear();

      if(!testPackingImages(testWidth, testHeight, testImagePlacement)) {
        if(imagePlacements.size() == 0) return imagePlacements;
        if(shrinkVertical) return imagePlacements;

        shrinkVertical = true;
        testWidth += smallestWidth + padding + padding;
        testHeight += smallestHeight + padding + padding;
        continue;
      }

      imagePlacements = new HashMap<BufferedImage, Rectangle>(testImagePlacement);

      testWidth = testHeight = 0;
      for(Map.Entry<BufferedImage, Rectangle> entry : imagePlacements.entrySet()) {
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
        if(shrinkVertical) return imagePlacements;
        shrinkVertical = true;
      }

      outputWidth = testWidth;
      outputHeight = testHeight;

      if(!shrinkVertical) testWidth -= smallestWidth;
      testHeight -= smallestHeight;
    }
  }

  private boolean testPackingImages(int testWidth, int testHeight, Map<BufferedImage, Rectangle> testImagePlacement) {
    ArevaloRectanglePacker rectanglePacker = new ArevaloRectanglePacker(testWidth, testHeight);

    for(BufferedImage image : images) {
      Point placement = rectanglePacker.tryPack(image.getWidth() + padding, image.getHeight() + padding);
      if(placement == null) return false;
      testImagePlacement.put(image, new Rectangle(placement.x, placement.y, image.getWidth() + padding, image.getHeight() + padding));
    }

    return true;
  }

  public BufferedImage generateOutputImage() {
    if(imagePlacements == null || imagePlacements.isEmpty()) {
      LOG.error("There is no image placement");
      return spriteImage;
    }
    spriteImage = new BufferedImage(outputWidth, outputHeight, BufferedImage.TYPE_INT_ARGB);
    for(Map.Entry<BufferedImage, Rectangle> entry : imagePlacements.entrySet()) {
      BufferedImage image = entry.getKey();
      Rectangle placement = entry.getValue();
      for(int y = 0; y < image.getHeight(); y++) {
        for(int x = 0; x < image.getWidth(); x++) {
          int rgb = image.getRGB(x, y);
          spriteImage.setRGB(x + placement.x, y + placement.y, rgb);
        }
      }
    }
    return spriteImage;
  }
}
