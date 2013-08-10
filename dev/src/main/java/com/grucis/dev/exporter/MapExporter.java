package com.grucis.dev.exporter;

import com.grucis.dev.model.export.map.TileMap;
import com.grucis.dev.model.setting.MapExportSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class MapExporter extends Exporter<TileMap> {

  @Autowired
  public MapExportSetting mapExportSetting;

  @Override
  public void export(TileMap model) throws Exception {
    String parent = mapExportSetting.getPath();
    String path = parent + "\\" + model.getId();
    prepareFolder(parent);
    writeObject(model, path + ".json");
  }
}
