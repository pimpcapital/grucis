package com.grucis.dev.exporter;

import com.grucis.dev.model.dictionary.map.MapDictionary;
import com.grucis.dev.model.setting.MapExportSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class MapDictionaryExporter extends Exporter<MapDictionary> {

  @Autowired
  public MapExportSetting mapExportSetting;

  @Override
  public void export(MapDictionary model) throws Exception {
    String parent = mapExportSetting.getPath();
    prepareFolder(parent);
    writeObject(model, parent + "\\dictionary.json");
  }
}
