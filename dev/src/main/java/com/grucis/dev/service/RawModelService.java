package com.grucis.dev.service;

import java.util.*;

import com.grucis.dev.deserializer.atomic.LS2MapDeserializer;
import com.grucis.dev.deserializer.data.RealDeserializer;
import com.grucis.dev.deserializer.data.SprDeserializer;
import com.grucis.dev.deserializer.index.AdrnDeserializer;
import com.grucis.dev.deserializer.index.SprAdrnDeserializer;
import com.grucis.dev.model.raw.atomic.LS2Map;
import com.grucis.dev.model.raw.data.Real;
import com.grucis.dev.model.raw.data.Spr;
import com.grucis.dev.model.raw.index.Adrn;
import com.grucis.dev.model.raw.index.SprAdrn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class RawModelService {

  @Autowired
  private AdrnDeserializer adrnDeserializer;
  @Autowired
  private SprAdrnDeserializer sprAdrnDeserializer;
  @Autowired
  private RealDeserializer realDeserializer;
  @Autowired
  private SprDeserializer sprDeserializer;
  @Autowired
  private LS2MapDeserializer ls2MapDeserializer;


  private Map<Integer, Adrn> mapElementMap;
  private Map<Integer, Adrn> adrnMap;
  private Map<Integer, SprAdrn> sprAdrnMap;
  private Map<Integer, LS2Map> ls2MapMap;

  private void prepareAdrns() {
    mapElementMap = new TreeMap<Integer, Adrn>();
    adrnMap = new TreeMap<Integer, Adrn>();
    for(Adrn adrn : adrnDeserializer.getRawModels()) {
      int id = adrn.getId();
      int meId = adrn.getMap();
      if(id > 12 && meId >= 100) {
        mapElementMap.put(meId, adrn);
      }
      adrnMap.put(adrn.getId(), adrn);
    }
  }

  private Map<Integer, Adrn> getMapElementMap() {
    if(mapElementMap == null) {
      prepareAdrns();
    }
    return mapElementMap;
  }

  private Map<Integer, Adrn> getAdrnMap() {
    if(adrnMap == null) {
      prepareAdrns();
    }
    return adrnMap;
  }

  private Map<Integer, SprAdrn> getSprAdrnMap() {
    if(sprAdrnMap == null) {
      sprAdrnMap = new TreeMap<Integer, SprAdrn>();
      for(SprAdrn sprAdrn : sprAdrnDeserializer.getRawModels()) {
        sprAdrnMap.put(sprAdrn.getId(), sprAdrn);
      }
    }
    return sprAdrnMap;
  }

  public Real getReal(Adrn index) {
    return realDeserializer.getRawModel(index.getAddress());
  }

  public List<Spr> getSprs(SprAdrn index) {
    List<Spr> ret = new ArrayList<Spr>();
    int address = index.getAddress();
    for(int i = 0; i < index.getActions(); i++) {
      Spr spr = sprDeserializer.getRawModel(address);
      address += sprDeserializer.getDataSize(spr);
      ret.add(spr);
    }
    return ret;
  }

  public Adrn getAdrn(int id) {
    return getAdrnMap().get(id);
  }

  public Adrn getAdrnByMapElementId(int id) {
    return getMapElementMap().get(id);
  }

  public Collection<Adrn> getAllAdrns() {
    return getAdrnMap().values();
  }

  public SprAdrn getSprAdrn(int id) {
    return getSprAdrnMap().get(id);
  }

  public Collection<SprAdrn> getAllSprAdrns() {
    return getSprAdrnMap().values();
  }

  private void prepareLS2Maps() {
    ls2MapMap = new TreeMap<Integer, LS2Map>();
    for(LS2Map ls2Map : ls2MapDeserializer.getRawModels(true)) {
      ls2MapMap.put(ls2Map.getId(), ls2Map);
    }
  }

  public Collection<LS2Map> getAllLS2Maps() {
    if(ls2MapMap == null) {
      prepareLS2Maps();
    }
    return ls2MapMap.values();
  }

  public LS2Map getLS2Map(int id) {
    if(ls2MapMap == null) {
      prepareLS2Maps();
    }
    LS2Map ls2Map = ls2MapMap.get(id);
    if(ls2Map != null) {
      ls2Map = ls2MapDeserializer.getRawModel(ls2Map.getPath(), false);
    }
    return ls2Map;
  }

  public LS2Map getLS2Map(String path) {
    return ls2MapDeserializer.getRawModel(path, false);
  }

}
