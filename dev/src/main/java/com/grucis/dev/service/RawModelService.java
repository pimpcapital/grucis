package com.grucis.dev.service;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;

import com.grucis.dev.deserializer.data.RealDeserializer;
import com.grucis.dev.deserializer.data.SprDeserializer;
import com.grucis.dev.deserializer.index.AdrnDeserializer;
import com.grucis.dev.deserializer.index.SprAdrnDeserializer;
import com.grucis.dev.model.raw.*;
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


  private Map<Integer, Adrn> mapElementMap;
  private Map<Integer, Adrn> adrnMap;
  private Map<Integer, SprAdrn> sprAdrnMap;

  private void prepareAdrns() {
    mapElementMap = new TreeMap<Integer, Adrn>();
    adrnMap = new TreeMap<Integer, Adrn>();
    for(Adrn adrn : adrnDeserializer.getRawModels()) {
      mapElementMap.put(adrn.getMap(), adrn);
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
    return realDeserializer.getRawModel(index);
  }

  public Spr getSpr(SprAdrn index) {
    return sprDeserializer.getRawModel(index);
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

}
