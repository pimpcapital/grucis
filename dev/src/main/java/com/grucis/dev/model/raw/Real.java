package com.grucis.dev.model.raw;

public final class Real extends RawModel {
  private String magic;
  private int major;
  private int minor;
  private int width;
  private int height;
  private int size;
  private byte[] data;

  public String getMagic() {
    return magic;
  }

  public void setMagic(String magic) {
    this.magic = magic;
  }

  public int getMajor() {
    return major;
  }

  public void setMajor(int major) {
    this.major = major;
  }

  public int getMinor() {
    return minor;
  }

  public void setMinor(int minor) {
    this.minor = minor;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }
}
