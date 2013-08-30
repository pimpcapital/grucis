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
    int[] mapElementTiles = source.getTiles();
    int[] mapElementObjects = source.getObjects();
    int total = south * east;
    int[] tiles = new int [total];
    int[] objects = new int [total];
    TreeSet<Integer> requires = new TreeSet<Integer>();
    for(int i = 0; i < total; i++) {
      Adrn tile = rawModelService.getAdrnByMapElementId(mapElementTiles[i]);
      if(tile != null) {
        int tileElement = tile.getId();
        tiles[i] = tileElement;
        requires.add(tileElement);
      } else tiles[i] = -1;
      Adrn object = rawModelService.getAdrnByMapElementId(mapElementObjects[i]);
      if(object != null) {
        int objectElement = object.getId();
        objects[i] = objectElement;
        requires.add(objectElement);
      } else objects[i] = -1;
    }
    ret.setTiles(tiles);
    ret.setObjects(objects);
    ret.setRequires(requires);
    return ret;
  }
}
