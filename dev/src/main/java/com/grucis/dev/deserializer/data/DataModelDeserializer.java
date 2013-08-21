package com.grucis.dev.deserializer.data;

import com.grucis.dev.deserializer.Deserializer;
import com.grucis.dev.model.raw.data.DataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DataModelDeserializer<DM extends DataModel> extends Deserializer<DM> {

  private static final Logger LOG = LoggerFactory.getLogger(DataModelDeserializer.class);

  private final DataType type;

  protected DataModelDeserializer(DataType type) {
    this.type = type;
  }

  protected abstract DM deserialize(int address) throws Exception;

  public final DM getRawModel(int address) {
    try {
      return deserialize(address);
    } catch(Exception e) {
      LOG.error("Cannot deserializeAll " + type + " data into raw models", e);
      return null;
    }
  }

  public abstract int getDataSize(DM model);

}
