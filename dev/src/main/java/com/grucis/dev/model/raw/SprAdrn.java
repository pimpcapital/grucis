package com.grucis.dev.model.raw;

public final class SprAdrn extends RawModel {
  private int animatrionId;
  private int address;
  private int actionNumber;
  private int unknown;

  public int getAnimatrionId() {
    return animatrionId;
  }

  public void setAnimatrionId(int animatrionId) {
    this.animatrionId = animatrionId;
  }

  public int getAddress() {
    return address;
  }

  public void setAddress(int address) {
    this.address = address;
  }

  public int getActionNumber() {
    return actionNumber;
  }

  public void setActionNumber(int actionNumber) {
    this.actionNumber = actionNumber;
  }

  public int getUnknown() {
    return unknown;
  }

  public void setUnknown(int unknown) {
    this.unknown = unknown;
  }
}
