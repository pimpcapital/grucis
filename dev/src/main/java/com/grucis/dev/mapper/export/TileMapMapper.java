package com.grucis.dev.mapper.export;

import com.grucis.dev.model.export.map.TileMap;
import com.grucis.dev.model.output.SaMap;
import org.springframework.stereotype.Component;

@Component
public final class TileMapMapper extends ExportModelMapper<SaMap, TileMap> {
  @Override
  public TileMap map(SaMap source) {
    TileMap ret = new TileMap(source.getId());

    ret.setName(source.getName());
    ret.setEast(source.getEast());
    ret.setSouth(source.getSouth());
    ret.setTiles(source.getTiles());
    ret.setObjects(source.getObjects());

    return ret;
  }
}
