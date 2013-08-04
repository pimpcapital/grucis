package com.grucis.dev.model.progress;

import java.util.List;

public final class BitmapExportProgress extends ProgressModel {
  private String current;
  private int progress;
  private int total;
  private double percent;
  private List<BitmapExportError> errors;
  private boolean finished;

  public String getCurrent() {
    return current;
  }

  public void setCurrent(String current) {
    this.current = current;
  }

  public int getProgress() {
    return progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public double getPercent() {
    return percent;
  }

  public void setPercent(double percent) {
    this.percent = percent;
  }

  public List<BitmapExportError> getErrors() {
    return errors;
  }

  public void setErrors(List<BitmapExportError> errors) {
    this.errors = errors;
  }

  public boolean isFinished() {
    return finished;
  }

  public void setFinished(boolean finished) {
    this.finished = finished;
  }

  public static class BitmapExportError {
    private String source;
    private String message;

    public BitmapExportError(String source, String message) {
      this.source = source;
      this.message = message;
    }

    public String getSource() {
      return source;
    }

    public void setSource(String source) {
      this.source = source;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }
}
