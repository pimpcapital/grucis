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

  private File findDataFile(final String prefix) {
    if(!dataFolder.isDirectory()) {
      LOG.error("Cannot find data directory {}", dataFolder.getAbsolutePath());
      return null;
    }
    return dataFolder.listFiles(new FilenameFilter() {
      public boolean accept(File dir, String name) {
        return StringUtils.startsWithIgnoreCase(name, prefix + "_");
      }
    })[0];
  }


  private InputStream getDataInputStream(String prefix) {
    try {
      return new FileInputStream(findDataFile(prefix));
    } catch(FileNotFoundException e) {
      LOG.error("Cannot get input stream on data file {} under {}", prefix, dataFolder.getAbsolutePath());
      return null;
    }
  }

  private RandomAccessFile getDataRandomAccessFile(String prefix) {
    try {
      return new RandomAccessFile(findDataFile(prefix), "r");
    } catch(FileNotFoundException e) {
      LOG.error("Cannot get random access on data file {} under {}", prefix, dataFolder.getAbsolutePath());
      return null;
    }
  }

  public InputStream getAdrn() {
    return getDataInputStream("ADRN");
  }

  public RandomAccessFile getReal() {
    return getDataRandomAccessFile("REAL");
  }

  public InputStream getSprAdrn() {
    return getDataInputStream("SPRADRN");
  }

  public RandomAccessFile getSpr() {
    return getDataRandomAccessFile("SPR");
  }




}
