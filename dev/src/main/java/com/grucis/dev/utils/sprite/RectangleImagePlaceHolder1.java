package com.grucis.dev.utils.sprite;

public final class RectangleImagePlaceHolder1 implements Comparable<RectangleImagePlaceHolder1> {
  private final int id;
  private final int width;
  private final int height;

  public RectangleImagePlaceHolder1(int id, int width, int height) {
    this.id = id;
    this.width = width;
    this.height = height;
  }

  public int getId() {
    return id;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int compareTo(RectangleImagePlaceHolder1 o) {
    int c = Integer.compare(o.getWidth(), getWidth());
    if(c != 0) return c;
    return Integer.compare(o.getHeight(), getHeight());
  }
}
