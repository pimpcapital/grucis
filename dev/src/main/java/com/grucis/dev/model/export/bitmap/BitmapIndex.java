package com.grucis.dev.model.export.bitmap;

public final class BitmapIndex {

  private int regX;
  private int regY;
  private int east;
  private int south;
  private boolean obstructive;


  public int getRegX() {
    return regX;
  }

  public void setRegX(int regX) {
    this.regX = regX;
  }

  public int getRegY() {
    return regY;
  }

  public void setRegY(int regY) {
    this.regY = regY;
  }

  public int getEast() {
    return east;
  }

  public void setEast(int east) {
    this.east = east;
  }

  public int getSouth() {
    return south;
  }

  public void setSouth(int south) {
    this.south = south;
  }

  public boolean getObstructive() {
    return obstructive;
  }

  public void setObstructive(boolean obstructive) {
    this.obstructive = obstructive;
  }
}
