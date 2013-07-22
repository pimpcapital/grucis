package com.grucis.dev.io;

import java.io.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public final class ResourceAllocator {

  private static final Logger LOG = LoggerFactory.getLogger(ResourceAllocator.class);

  @Autowired
  @Qualifier("data")
  private File dataFolder;

  @Autowired
  @Qualifier("map")
  private File mapFolder;


  private InputStream getDataFile(final String prefix) {
    File adrn = dataFolder.listFiles(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return StringUtils.startsWithIgnoreCase(name, prefix + "_");
      }
    })[0];
    try {
      return new FileInputStream(adrn);
    } catch(FileNotFoundException e) {
      LOG.error("Data file " + prefix + " cannot be found", e);
      return null;
    }
  }

  public InputStream getAdrn() {
    return getDataFile("ADRN");
  }

  public InputStream getReal() {
    return getDataFile("REAL");
  }

  public InputStream getSprAdrn() {
    return getDataFile("SPRADRN");
  }

  public InputStream getSpr() {
    return getDataFile("SPR");
  }




}
