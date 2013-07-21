package com.grucis.dev.model.raw;

public final class SprAdrn extends RawModel {
  private int id;
  private int address;
  private int actions;
  private int sound;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getAddress() {
    return address;
  }

  public void setAddress(int address) {
    this.address = address;
  }

  public int getActions() {
    return actions;
  }

  public void setActions(int actions) {
    this.actions = actions;
  }

  public int getSound() {
    return sound;
  }

  public void setSound(int sound) {
    this.sound = sound;
  }
}
