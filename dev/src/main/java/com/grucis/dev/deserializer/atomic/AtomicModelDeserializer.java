package com.grucis.dev.deserializer.atomic;

import java.util.Collection;

import com.grucis.dev.deserializer.Deserializer;
import com.grucis.dev.model.raw.atomic.AtomicModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AtomicModelDeserializer<AM extends AtomicModel> extends Deserializer<AM> {

  private static final Logger LOG = LoggerFactory.getLogger(AtomicModelDeserializer.class);

  private final AtomicType type;

  protected AtomicModelDeserializer(AtomicType type) {
    this.type = type;
  }

  protected abstract Collection<AM> deserializeAll(boolean headerOnly) throws Exception;

  public abstract AM getRawModel(String path, boolean headerOnly);

  public final Collection<AM> getRawModels(boolean headerOnly) {
    try {
      return deserializeAll(headerOnly);
    } catch(Exception e) {
      LOG.error("Cannot deserializeAll " + type + " data into raw models", e);
      return null;
    }
  }

}
