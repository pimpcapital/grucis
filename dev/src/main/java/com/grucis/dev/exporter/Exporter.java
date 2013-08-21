package com.grucis.dev.exporter;

import java.awt.image.BufferedImage;
import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grucis.dev.utils.image.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Exporter<XM extends Exportable> {

  private static final Logger LOG = LoggerFactory.getLogger(Exporter.class);

  private Gson gson;

  protected boolean prepareFolder(String path) {
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

  protected void writeImage(BufferedImage image, String path) throws Exception {
    byte[] bytes = ImageUtils.toBytes(image);
    File file = new File(path);
    if(file.exists()) file.delete();
    FileOutputStream output = new FileOutputStream(file);
    output.write(bytes);
    output.close();
  }

  protected void writeObject(Object object, String path) throws Exception {
    String placements = gson().toJson(object);
    File file = new File(path);
    if(file.exists()) file.delete();
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    writer.write(placements);
    writer.close();
  }

  public abstract void export(XM model) throws Exception;

}
