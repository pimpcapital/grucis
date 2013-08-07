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
    File file = findDataFile(prefix);
    try {
      return new FileInputStream(file);
    } catch(FileNotFoundException e) {
      LOG.error("Cannot get input stream on missing data file {} under {}", file.getAbsolutePath(), dataFolder.getAbsolutePath());
      return null;
    }
  }

  private RandomAccessFile getDataRandomAccessFile(String prefix) {
    File file = findDataFile(prefix);
    try {
      return new RandomAccessFile(file, "r");
    } catch(FileNotFoundException e) {
      LOG.error("Cannot get random access on missing data file {} under {}", file.getAbsolutePath(), dataFolder.getAbsolutePath());
      return null;
    }
  }

  public InputStream getPaletteInputStream(final String prefix) {
    File[] paletteFolder = dataFolder.listFiles(new FileFilter() {
      public boolean accept(File pathname) {
        return pathname.getName().equalsIgnoreCase("pal") && pathname.isDirectory();
      }
    });
    if(paletteFolder.length != 1) {
      LOG.error("Cannot find palette folder under {}", dataFolder.getAbsolutePath());
      return null;
    }
    File[] pal = paletteFolder[0].listFiles(new FilenameFilter() {
      public boolean accept(File dir, String name) {
        return StringUtils.startsWithIgnoreCase(name, prefix);
      }
    });
    if(pal.length < 1) {
      LOG.error("Cannot find palette file with prefix {} under {}", prefix, paletteFolder[0].getAbsolutePath());
      return null;
    }
    try {
      return new FileInputStream(pal[0]);
    } catch(FileNotFoundException e) {
      LOG.error("Cannot get input stream on missing palette file {} under {}", pal[0].getAbsolutePath(), paletteFolder[0].getAbsolutePath());
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
