package com.grucis.dev.mapper;

import java.io.InputStream;
import java.util.Collection;

import com.grucis.dev.model.raw.RawModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Mapper<RM extends RawModel> {

  private static final Logger LOG = LoggerFactory.getLogger(Mapper.class);

  private InputStream in;

  protected Mapper(InputStream in) {
    this.in = in;
  }

  protected abstract Collection<RM> generateModels(InputStream in) throws Exception;

  public final Collection<RM> getRawModels() {
    try {
      return generateModels(in);
    } catch(Exception e) {
      LOG.error("Cannot generate model", e);
      return null;
    }
  }



}
