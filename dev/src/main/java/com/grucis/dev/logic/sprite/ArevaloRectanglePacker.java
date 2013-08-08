package com.grucis.dev.logic.sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.grucis.dev.utils.sprite.Anchor;
import com.grucis.dev.utils.sprite.RectanglePlacement;
import com.grucis.dev.utils.sprite.SpriteUtils;

public final class ArevaloRectanglePacker {

  private int packingAreaWidth;
  private int packingAreaHeight;

  private int actualPackingAreaHeight = 1;
  private int actualPackingAreaWidth = 1;
  private List<Anchor> anchors = new ArrayList<Anchor>();
  private List<RectanglePlacement> packedRectangles = new ArrayList<RectanglePlacement>();

  public ArevaloRectanglePacker(int packingAreaWidth, int packingAreaHeight) {
    this.packingAreaWidth = packingAreaWidth;
    this.packingAreaHeight = packingAreaHeight;
    anchors.add(new Anchor(0, 0));
  }

  public Anchor tryPack(int rectangleWidth, int rectangleHeight) {
    int anchorIndex = selectAnchorRecursive(rectangleWidth, rectangleHeight, actualPackingAreaWidth, actualPackingAreaHeight);

    if(anchorIndex == -1) return null;

    Anchor placement = anchors.get(anchorIndex);
    optimizePlacement(placement, rectangleWidth, rectangleHeight);
    boolean blocksAnchor =
      ((placement.getX() + rectangleWidth) > anchors.get(anchorIndex).getX()) &&
        ((placement.getY() + rectangleHeight) > anchors.get(anchorIndex).getY());

    if(blocksAnchor)
      anchors.remove(anchorIndex);

    insertAnchor(new Anchor(placement.getX() + rectangleWidth, placement.getY()));
    insertAnchor(new Anchor(placement.getX(), placement.getY() + rectangleHeight));
    packedRectangles.add(new RectanglePlacement(placement.getX(), placement.getY(), rectangleWidth, rectangleHeight));

    return placement;
  }

  private void optimizePlacement(Anchor placement, int rectangleWidth, int rectangleHeight) {
    RectanglePlacement rectangle = new RectanglePlacement(placement.getX(), placement.getY(), rectangleWidth, rectangleHeight);

    int leftMost = placement.getX();
    while(isFree(rectangle, packingAreaWidth, packingAreaHeight)) {
      leftMost = rectangle.getX();
      rectangle.setX(leftMost - 1);
    }
    rectangle.setX(placement.getX());

    int topMost = placement.getY();
    while(isFree(rectangle, packingAreaWidth, packingAreaHeight)) {
      topMost = rectangle.getY();
      rectangle.setY(topMost - 1);
    }

    if((placement.getX() - leftMost) > (placement.getY() - topMost)) placement.setX(leftMost);
    else placement.setY(topMost);
  }

  private int selectAnchorRecursive(int rectangleWidth, int rectangleHeight, int testedPackingAreaWidth, int testedPackingAreaHeight) {
    int freeAnchorIndex = findFirstFreeAnchor(rectangleWidth, rectangleHeight, testedPackingAreaWidth, testedPackingAreaHeight);
    if(freeAnchorIndex != -1) {
      actualPackingAreaWidth = testedPackingAreaWidth;
      actualPackingAreaHeight = testedPackingAreaHeight;
      return freeAnchorIndex;
    }
    boolean canEnlargeWidth = (testedPackingAreaWidth < packingAreaWidth);
    boolean canEnlargeHeight = (testedPackingAreaHeight < packingAreaHeight);
    boolean shouldEnlargeHeight = (!canEnlargeWidth) || (testedPackingAreaHeight < testedPackingAreaWidth);

    if(canEnlargeHeight && shouldEnlargeHeight) {
      return selectAnchorRecursive(rectangleWidth, rectangleHeight, testedPackingAreaWidth, Math.min(testedPackingAreaHeight * 2, packingAreaHeight));
    }
    if(canEnlargeWidth) {
      return selectAnchorRecursive(rectangleWidth, rectangleHeight, Math.min(testedPackingAreaWidth * 2, packingAreaWidth), testedPackingAreaHeight);
    }

    return -1;
  }

  private int findFirstFreeAnchor(int rectangleWidth, int rectangleHeight, int testedPackingAreaWidth, int testedPackingAreaHeight) {
    RectanglePlacement potentialLocation = new RectanglePlacement(0, 0, rectangleWidth, rectangleHeight);

    for(int index = 0; index < anchors.size(); ++index) {
      potentialLocation.setX(anchors.get(index).getX());
      potentialLocation.setY(anchors.get(index).getY());

      if(isFree(potentialLocation, testedPackingAreaWidth, testedPackingAreaHeight))
        return index;
    }

    return -1;
  }



  private boolean isFree(RectanglePlacement rectangle, int testedPackingAreaWidth, int testedPackingAreaHeight) {
    boolean leavesPackingArea = (rectangle.getX() < 0)
                                  || (rectangle.getY() < 0)
                                  || (rectangle.getX() + rectangle.getWidth() > testedPackingAreaWidth)
                                  || (rectangle.getY() + rectangle.getHeight() > testedPackingAreaHeight);
    if(leavesPackingArea) return false;

    for(RectanglePlacement packedRectangle : packedRectangles) {
      if(SpriteUtils.intersects(packedRectangle, rectangle)) return false;
    }

    return true;
  }

  private void insertAnchor(Anchor anchor) {
    int insertIndex = Collections.binarySearch(anchors, anchor);
    if(insertIndex < 0) insertIndex = ~insertIndex;

    anchors.add(insertIndex, anchor);
  }

}
