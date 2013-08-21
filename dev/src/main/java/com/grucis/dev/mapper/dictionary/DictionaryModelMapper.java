package com.grucis.dev.mapper.dictionary;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import com.grucis.dev.mapper.ModelMapper;
import com.grucis.dev.model.Model;
import com.grucis.dev.model.dictionary.DictionaryEntry;

public abstract class DictionaryModelMapper<M extends Model, DE extends DictionaryEntry> extends ModelMapper<M, DE> {

  public Map<Integer, DE> createDictionary(Collection<M> models) {
    Map<Integer, DE> ret = new TreeMap<Integer, DE>();

    for(M model : models) {
      DE entry = map(model);
      ret.put(entry.getId(), entry);
    }

    return ret;
  }
}
