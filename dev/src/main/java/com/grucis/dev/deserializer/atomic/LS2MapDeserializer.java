package com.grucis.dev.deserializer.atomic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.grucis.dev.io.ResourceAllocator;
import com.grucis.dev.model.raw.atomic.LS2Map;
import com.grucis.dev.utils.bitwise.BitwiseUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class LS2MapDeserializer extends AtomicModelDeserializer<LS2Map> {

  private static final Logger LOG = LoggerFactory.getLogger(LS2MapDeserializer.class);

  @Autowired
  private ResourceAllocator resourceAllocator;

  public LS2MapDeserializer() {
    super(AtomicType.LS2MAP);
  }

  @Override
  protected Collection<LS2Map> deserialize() throws Exception {
    Map<String, InputStream> inputs = resourceAllocator.getMapInputStreams();

    Collection<LS2Map> ret = new ArrayList<LS2Map>();
    for(Map.Entry<String, InputStream> entry : inputs.entrySet()) {
      InputStream input = entry.getValue();
      LS2Map ls2Map = new LS2Map();

      byte[] versionBytes = new byte[6];
      input.read(versionBytes);
      String version = new String(versionBytes);
      if(!version.equals("LS2MAP")) {
        if(StringUtils.isAsciiPrintable(version)) {
          LOG.error("Unrecognized map version {} in {}", version, entry.getKey());
        }
        input.close();
        continue;
      }
      ls2Map.setVersion(version);

      byte[] idBytes = new byte[2];
      input.read(idBytes);
      int id = BitwiseUtils.uint16(idBytes);
      ls2Map.setId(id);

      byte[] nameBytes = new byte[32];
      input.read(nameBytes);
      String name = new String(nameBytes, "gbk");
      name = name.substring(0, Math.min(name.indexOf('|'), name.indexOf('\u0000')));
      ls2Map.setName(name);

      byte[] eastBytes = new byte[2];
      input.read(eastBytes);
      int east = BitwiseUtils.uint16(eastBytes);
      ls2Map.setEast(east);

      byte[] southBytes = new byte[2];
      input.read(southBytes);
      int south = BitwiseUtils.uint16(eastBytes);
      ls2Map.setSouth(south);

      int max = east * south;
      int[] tiles = new int[max];
      for(int i = 0; i < max; i++) {
        byte[] tileBytes = new byte[2];
        input.read(tileBytes);
        tiles[i] = BitwiseUtils.uint16(tileBytes);
      }
      ls2Map.setTiles(tiles);

      int[] objects = new int[max];
      for(int i = 0; i < max; i++) {
        byte[] objectBytes = new byte[2];
        input.read(objectBytes);
        objects[i] = BitwiseUtils.uint16(objectBytes);
      }
      ls2Map.setObjects(objects);

      ret.add(ls2Map);
      input.close();
    }

    return ret;
  }
}
