package com.grucis.dev.exporter;

import java.io.*;

import com.grucis.dev.model.output.OutputModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Exporter<OM extends OutputModel> {

  private static final Logger LOG = LoggerFactory.getLogger(Exporter.class);

  public abstract byte[] toByteArray(OutputModel model);

  public final void export(String filePath, OutputModel model) {
    export(new File(filePath), model);
  }

  public final void export(File file, OutputModel model) {
     try {
       FileOutputStream out = new FileOutputStream(file);
       out.write(toByteArray(model));
       out.close();
     } catch(FileNotFoundException e) {
       LOG.error("Cannot get output stream on missing file {}", file.getAbsolutePath());
     } catch(IOException e) {
       LOG.error("Cannot export to file {}", file.getAbsolutePath());
     }
  }
}
