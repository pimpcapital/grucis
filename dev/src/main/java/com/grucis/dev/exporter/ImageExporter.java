package com.grucis.dev.exporter;

import com.grucis.dev.model.output.OffsetImage;
import com.grucis.dev.model.output.OutputModel;
import org.springframework.stereotype.Component;

@Component
public final class ImageExporter extends Exporter<OffsetImage> {
  @Override
  public byte[] toByteArray(OutputModel model) {
    return new byte[0];
  }
}
