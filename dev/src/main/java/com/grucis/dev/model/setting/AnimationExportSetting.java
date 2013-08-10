package com.grucis.dev.model.setting;

public final class AnimationExportSetting extends SettingModel {
  private String path;
  private String format;

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }
}
