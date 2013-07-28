package com.grucis.dev.exporter;

import java.io.*;

import com.grucis.dev.model.output.OutputModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Exporter<OM extends OutputModel> {

  private static final Logger LOG = LoggerFactory.getLogger(Exporter.class);

  public static final String APPENDIX_FILE_SUFFIX = ".txt";

  public abstract byte[] getBinaryData(OM model);

  public String getAppendix(OM model) {
    return null;
  }

  public final void export(String filePath, OM model) {
    export(new File(filePath), model);
  }

  public final void export(File file, OM model) {
    try {
      FileOutputStream out = new FileOutputStream(file);
      out.write(getBinaryData(model));
      out.close();
    } catch(FileNotFoundException e) {
      LOG.error("Cannot get output stream on missing file {}", file.getAbsolutePath());
    } catch(IOException e) {
      LOG.error("Cannot export to file {}", file.getAbsolutePath());
    }

    try {
      String appx = getAppendix(model);
      if(appx != null) {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath() + APPENDIX_FILE_SUFFIX));
        writer.write(appx);
        writer.close();
      }
    } catch(IOException e) {
      LOG.error("Cannot export appendix file to {}" + APPENDIX_FILE_SUFFIX, file.getAbsolutePath());
    }
  }
}
