package com.grucis.dev.model.dictionary;

import java.util.Map;

import com.grucis.dev.exporter.Exportable;

public abstract class DictionaryModel<DE extends DictionaryEntry> implements Exportable {

  private Map<Integer, DE> dictionary;

  public Map<Integer, DE> getDictionary() {
    return dictionary;
  }

  public void setDictionary(Map<Integer, DE> dictionary) {
    this.dictionary = dictionary;
  }
}
