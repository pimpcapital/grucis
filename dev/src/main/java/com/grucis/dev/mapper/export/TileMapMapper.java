package com.grucis.dev.mapper.export;

import com.grucis.dev.model.export.map.TileMap;
import com.grucis.dev.model.output.SaMap;
import com.grucis.dev.service.RawModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class TileMapMapper extends ExportModelMapper<SaMap, TileMap> {

  @Autowired
  private RawModelService rawModelService;

  @Override
  public TileMap map(SaMap source) {
    TileMap ret = new TileMap(source.getId());

    ret.setName(source.getName());
    int east = source.getEast();
    ret.setEast(east);
    int south = source.getSouth();
    ret.setSouth(south);
    int[][] mapElementTiles = source.getTiles();
    int[][] mapElementObjects = source.getObjects();
    int[][] tiles = new int [south][east];
    int[][] objects = new int [south][east];
    for(int s = 0; s < south; s++) {
      for(int e = 0; e < east; e++) {
        tiles[s][e] = rawModelService.getAdrnByMapElementId(mapElementTiles[s][e]).getId();
        objects[s][e] = rawModelService.getAdrnByMapElementId(mapElementObjects[s][e]).getId();
      }
    }
    ret.setTiles(tiles);
    ret.setObjects(objects);

    return ret;
  }
}
