package com.grucis.dev.mapper.export;

import java.util.TreeSet;

import com.grucis.dev.model.export.map.TileMap;
import com.grucis.dev.model.output.SaMap;
import com.grucis.dev.model.raw.index.Adrn;
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
    TreeSet<Integer> require = new TreeSet<Integer>();
    for(int s = 0; s < south; s++) {
      for(int e = 0; e < east; e++) {
        Adrn tile = rawModelService.getAdrnByMapElementId(mapElementTiles[s][e]);
        if(tile != null) {
          int tileElement = tile.getId();
          tiles[s][e] = tileElement;
          require.add(tileElement);
        } else {
          tiles[s][e] = -1;
        }
        Adrn object = rawModelService.getAdrnByMapElementId(mapElementObjects[s][e]);
        if(object != null) {
          int objectElement = object.getId();
          objects[s][e] = objectElement;
          require.add(objectElement);
        }
        objects[s][e] = object != null ? object.getId() : -1;
      }
    }
    ret.setTiles(tiles);
    ret.setObjects(objects);

    return ret;
  }
}
