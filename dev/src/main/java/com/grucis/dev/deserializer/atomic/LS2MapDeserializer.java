package com.grucis.dev.deserializer.atomic;

import java.io.FileInputStream;
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

  private LS2Map deserialize(InputStream input, String path, boolean headerOnly) throws Exception {
    LS2Map ret = new LS2Map(path);

    byte[] versionBytes = new byte[6];
    input.read(versionBytes);
    String version = new String(versionBytes);
    if(!version.equals("LS2MAP")) {
      if(StringUtils.isAsciiPrintable(version)) {
        LOG.error("Unrecognized map version {} in {}", version, path);
      }
      input.close();
      return null;
    }
    ret.setVersion(version);

    byte[] idBytes = new byte[2];
    input.read(idBytes);
    int id = BitwiseUtils.uint16BE(idBytes);
    ret.setId(id);

    byte[] nameBytes = new byte[32];
    input.read(nameBytes);
    String name = new String(nameBytes, "gbk");
    int length = name.indexOf('|');
    if(length != -1) name = name.substring(0, length);
    length = name.indexOf('\u0000');
    if(length != -1) name = name.substring(0, length);
    ret.setName(name);

    byte[] eastBytes = new byte[2];
    input.read(eastBytes);
    int east = BitwiseUtils.uint16BE(eastBytes);
    ret.setEast(east);

    byte[] southBytes = new byte[2];
    input.read(southBytes);
    int south = BitwiseUtils.uint16BE(southBytes);
    ret.setSouth(south);

    if(!headerOnly) {
      int max = east * south;
      int[] tiles = new int[max];
      for(int i = 0; i < max; i++) {
        byte[] tileBytes = new byte[2];
        input.read(tileBytes);
        tiles[i] = BitwiseUtils.uint16BE(tileBytes);
      }
      ret.setTiles(tiles);

      int[] objects = new int[max];
      for(int i = 0; i < max; i++) {
        byte[] objectBytes = new byte[2];
        input.read(objectBytes);
        objects[i] = BitwiseUtils.uint16BE(objectBytes);
      }
      ret.setObjects(objects);
    }
    return ret;
  }

  @Override
  public LS2Map getRawModel(String path, boolean headerOnly) {
    try {
      return deserialize(new FileInputStream(path), path, headerOnly);
    } catch(Exception e) {
      LOG.error("Cannot deserialize LS2MAP in {}", path);
      return null;
    }
  }

  @Override
  protected Collection<LS2Map> deserializeAll(boolean headerOnly) throws Exception {
    Map<String, InputStream> inputs = resourceAllocator.getMapInputStreams();

    Collection<LS2Map> ret = new ArrayList<LS2Map>();
    for(Map.Entry<String, InputStream> entry : inputs.entrySet()) {
      InputStream input = entry.getValue();
      String path = entry.getKey();
      LS2Map ls2Map = deserialize(input, path, headerOnly);
      if(ls2Map != null) ret.add(ls2Map);
      input.close();
    }

    return ret;
  }
}
