package com.grucis.dev.exporter;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grucis.dev.model.export.ExportModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Exporter<XM extends ExportModel> {

  private static final Logger LOG = LoggerFactory.getLogger(Exporter.class);

  private Gson gson;

  public boolean prepareFolder(String path) {
    File folder = new File(path);
    if(!folder.exists() && !folder.mkdirs() && !folder.isDirectory()) {
      LOG.error("Cannot create folder {}", path);
      return false;
    }
    return true;
  }

  protected Gson gson() {
    if(gson == null) {
      gson = new GsonBuilder().setPrettyPrinting().create();
    }
    return gson;
  }

  public abstract void export(XM model) throws Exception;

}
