package com.grucis.dev.deserializer;

import java.io.InputStream;
import java.util.Collection;

import com.grucis.dev.model.raw.RawModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Deserializer<RM extends RawModel> {

  private static final Logger LOG = LoggerFactory.getLogger(Deserializer.class);

  private InputStream in;
  private DataType type;

  protected Deserializer(InputStream in, DataType type) {
    this.in = in;
    this.type = type;
  }

  protected abstract Collection<RM> deserialize(InputStream in) throws Exception;

  public final Collection<RM> getRawModels() {
    try {
      return deserialize(in);
    } catch(Exception e) {
      LOG.error("Cannot deserialize " + type + " data into raw models", e);
      return null;
    }
  }



}
