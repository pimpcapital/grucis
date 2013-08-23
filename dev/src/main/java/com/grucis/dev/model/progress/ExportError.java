package com.grucis.dev.model.progress;

public final class ExportError {
  private final String source;
  private final String message;

  public ExportError(String source, String message) {
    this.source = source;
    this.message = message;
  }

  public String getSource() {
    return source;
  }

  public String getMessage() {
    return message;
  }

}
