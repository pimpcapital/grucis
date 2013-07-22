package com.grucis.dev.deserializer.index;

import java.io.InputStream;
import java.util.Collection;

import com.grucis.dev.model.raw.RawModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class IndexModelDeserializer<RM extends RawModel> {

  private static final Logger LOG = LoggerFactory.getLogger(IndexModelDeserializer.class);

  private IndexType type;

  protected IndexModelDeserializer(InputStream in, IndexType type) {
    this.type = type;
  }

  protected abstract Collection<RM> deserialize() throws Exception;

  public final Collection<RM> getRawModels() {
    try {
      return deserialize();
    } catch(Exception e) {
      LOG.error("Cannot deserialize " + type + " data into raw models", e);
      return null;
    }
  }



}
