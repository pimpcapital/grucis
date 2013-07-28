package com.grucis.dev.exporter;

import com.grucis.dev.model.output.OffsetImage;
import org.springframework.stereotype.Component;

@Component
public final class ImageExporter extends Exporter<OffsetImage> {
  @Override
  public byte[] getBinaryData(OffsetImage model) {
    return new byte[0];
  }
}
