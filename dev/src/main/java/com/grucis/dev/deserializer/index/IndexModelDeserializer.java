package com.grucis.dev.deserializer.index;

import java.util.Collection;

import com.grucis.dev.deserializer.Deserializer;
import com.grucis.dev.model.raw.index.IndexModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class IndexModelDeserializer<IM extends IndexModel> extends Deserializer<IM> {

  private static final Logger LOG = LoggerFactory.getLogger(IndexModelDeserializer.class);

  private final IndexType type;

  protected IndexModelDeserializer(IndexType type) {
    this.type = type;
  }

  protected abstract Collection<IM> deserialize() throws Exception;

  public final Collection<IM> getRawModels() {
    try {
      return deserialize();
    } catch(Exception e) {
      LOG.error("Cannot deserializeAll " + type + " data into raw models", e);
      return null;
    }
  }



}
