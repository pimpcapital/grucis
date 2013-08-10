package com.grucis.dev.mapper.output;

import com.grucis.dev.model.output.SaMap;
import com.grucis.dev.model.raw.atomic.LS2Map;
import org.springframework.stereotype.Component;

@Component
public final class SaMapMapper extends OutputModelMapper<LS2Map, SaMap> {

  @Override
  public SaMap map(LS2Map source) {
    SaMap ret = new SaMap(source.getId());

    ret.setName(source.getName());
    int east = source.getEast();
    ret.setEast(east);
    int south = source.getSouth();
    ret.setSouth(south);

    int[][] tiles = new int[south][east];
    int[][] objects = new int[south][east];
    int[] sourceTiles = source.getTiles();
    int[] sourceObjects = source.getObjects();
    int count = 0;
    for(int s = 0; s < south; s++) {
      for(int e = 0; e < east; e++) {
        tiles[s][e] = sourceTiles[count];
        objects[s][e] = sourceObjects[count];
        count++;
      }
    }
    ret.setTiles(tiles);
    ret.setObjects(objects);

    return ret;
  }
}
