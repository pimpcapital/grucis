package com.grucis.dev.logic.sprite;

import java.util.*;

import com.grucis.dev.utils.math.IntegerUtils;
import com.grucis.dev.utils.sprite.Anchor;
import com.grucis.dev.utils.sprite.RectangleImagePlaceholder;
import com.grucis.dev.utils.sprite.RectanglePlacement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SpriteSheetPacker {

  private Logger LOG = LoggerFactory.getLogger(SpriteSheetPacker.class);

  private List<RectangleImagePlaceholder> images = new ArrayList<RectangleImagePlaceholder>();
  private Map<Integer, RectanglePlacement> placements;
  private int padding = 2;
  private boolean requirePow2 = false;
  private boolean requireSquare = false;
  private int outputWidth = 8192;
  private int outputHeight = 8192;

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

  public void addImage(RectangleImagePlaceholder image) {
    images.add(image);
  }

  public void addImages(Collection<RectangleImagePlaceholder> images) {
    this.images.addAll(images);
  }

  public Map<Integer, RectanglePlacement> packImageRectangles() {
    if(images.isEmpty()) {
      LOG.error("There is no image to pack");
      return placements;
    }

    Collections.sort(images);

    Map<Integer, RectanglePlacement> testImagePlacement = new TreeMap<Integer, RectanglePlacement>();

    int smallestWidth = Integer.MAX_VALUE;
    int smallestHeight = Integer.MAX_VALUE;
    for(RectangleImagePlaceholder image : images) {
      smallestWidth = Math.min(smallestWidth, image.getWidth());
      smallestHeight = Math.min(smallestHeight, image.getHeight());
    }

    int testWidth = outputWidth;
    int testHeight = outputHeight;
    boolean shrinkVertical = false;
    while(true) {
      testImagePlacement.clear();

      if(!testPackingImages(testWidth, testHeight, testImagePlacement)) {
        if(placements == null) return placements;
        if(shrinkVertical) return placements;

        shrinkVertical = true;
        testWidth += smallestWidth + padding + padding;
        testHeight += smallestHeight + padding + padding;
        continue;
      }

      placements = new HashMap<Integer, RectanglePlacement>(testImagePlacement);

      testWidth = testHeight = 0;
      for(Map.Entry<Integer, RectanglePlacement> entry : placements.entrySet()) {
        RectanglePlacement placement = entry.getValue();
        testWidth = Math.max(testWidth, placement.getX() + placement.getWidth());
        testHeight = Math.max(testHeight, placement.getY() + placement.getHeight());
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
        if(shrinkVertical) return placements;
        shrinkVertical = true;
      }

      outputWidth = testWidth;
      outputHeight = testHeight;

      if(!shrinkVertical) testWidth -= smallestWidth;
      testHeight -= smallestHeight;
    }
  }

  private boolean testPackingImages(int testWidth, int testHeight, Map<Integer, RectanglePlacement> testImagePlacement) {
    ArevaloRectanglePacker rectanglePacker = new ArevaloRectanglePacker(testWidth, testHeight);

    for(RectangleImagePlaceholder image : images) {
      Anchor placement = rectanglePacker.tryPack(image.getWidth() + padding, image.getHeight() + padding);
      if(placement == null) return false;
      testImagePlacement.put(image.getId(), new RectanglePlacement(placement.getX(), placement.getY(), image.getWidth() + padding, image.getHeight() + padding));
    }

    return true;
  }
}
