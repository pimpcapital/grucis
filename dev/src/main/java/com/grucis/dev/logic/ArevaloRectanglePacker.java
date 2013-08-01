package com.grucis.dev.logic;

import java.awt.*;
import java.util.*;
import java.util.List;

public final class ArevaloRectanglePacker {

  private int packingAreaWidth;
  private int packingAreaHeight;

  private int actualPackingAreaHeight = 1;
  private int actualPackingAreaWidth = 1;
  private List<Point> anchors = new ArrayList<Point>();
  private List<Rectangle> packedRectangles = new ArrayList<Rectangle>();
  private Comparator<Point> anchorRankComparator = new Comparator<Point>() {
    public int compare(Point p1, Point p2) {
      return (p1.x + p1.y) - (p2.x + p2.y);
    }
  };

  public ArevaloRectanglePacker(int packingAreaWidth, int packingAreaHeight) {
    this.packingAreaWidth = packingAreaWidth;
    this.packingAreaHeight = packingAreaHeight;
  }

  public Point tryPack(int rectangleWidth, int rectangleHeight) {
    int anchorIndex = selectAnchorRecursive(rectangleWidth, rectangleHeight, actualPackingAreaWidth, actualPackingAreaHeight);

    if(anchorIndex == -1) return null;

    Point placement = anchors.get(anchorIndex);
    optimizePlacement(placement, rectangleWidth, rectangleHeight);
    boolean blocksAnchor =
      ((placement.x + rectangleWidth) > anchors.get(anchorIndex).x) &&
        ((placement.y + rectangleHeight) > anchors.get(anchorIndex).y);

    if(blocksAnchor)
      anchors.remove(anchorIndex);

    insertAnchor(new Point(placement.x + rectangleWidth, placement.y));
    insertAnchor(new Point(placement.x, placement.y + rectangleHeight));
    packedRectangles.add(new Rectangle(placement.x, placement.y, rectangleWidth, rectangleHeight));

    return placement;
  }

  private void optimizePlacement(Point placement, int rectangleWidth, int rectangleHeight) {
    Rectangle rectangle = new Rectangle(placement.x, placement.y, rectangleWidth, rectangleHeight);

    int leftMost = placement.x;
    while(isFree(rectangle, packingAreaWidth, packingAreaHeight)) {
      leftMost = rectangle.x;
      --rectangle.x;
    }
    rectangle.x = placement.x;

    int topMost = placement.y;
    while(isFree(rectangle, packingAreaWidth, packingAreaHeight)) {
      topMost = rectangle.y;
      --rectangle.y;
    }

    if((placement.x - leftMost) > (placement.y - topMost)) placement.x = leftMost;
    else placement.y = topMost;
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
    Rectangle potentialLocation = new Rectangle(0, 0, rectangleWidth, rectangleHeight);

    for(int index = 0; index < anchors.size(); ++index) {
      potentialLocation.x = anchors.get(index).x;
      potentialLocation.y = anchors.get(index).y;

      if(isFree(potentialLocation, testedPackingAreaWidth, testedPackingAreaHeight))
        return index;
    }

    return -1;
  }

  private boolean isFree(Rectangle rectangle, int testedPackingAreaWidth, int testedPackingAreaHeight) {
    boolean leavesPackingArea = (rectangle.x < 0) || (rectangle.y < 0) || (rectangle.getMaxX() > testedPackingAreaWidth) || (rectangle.getMaxY() > testedPackingAreaHeight);
    if(leavesPackingArea) return false;

    for(Rectangle packedRectangle : packedRectangles) {
      if(packedRectangle.intersects(rectangle))
        return false;
    }

    return true;
  }

  private void insertAnchor(Point anchor) {
    int insertIndex = Collections.binarySearch(anchors, anchor, anchorRankComparator);
    if(insertIndex < 0) insertIndex = ~insertIndex;

    anchors.add(insertIndex, anchor);
  }

}
