package com.grucis.dev.deserializer.data;

import com.grucis.dev.model.raw.RawModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DataModelDeserializer<RM extends RawModel, IM extends RawModel> {

  private static final Logger LOG = LoggerFactory.getLogger(DataModelDeserializer.class);

  private DataType type;

  protected DataModelDeserializer(DataType type) {
    this.type = type;
  }

  protected abstract RM deserialize(IM index) throws Exception;

  public final RM getRawModel(IM index) {
    try {
      return deserialize(index);
    } catch(Exception e) {
      LOG.error("Cannot deserialize " + type + " data into raw models", e);
      return null;
    }
  }



}
