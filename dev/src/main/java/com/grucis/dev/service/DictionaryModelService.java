package com.grucis.dev.service;

import java.util.Collection;
import java.util.Map;

import com.grucis.dev.exporter.MapDictionaryExporter;
import com.grucis.dev.io.ModelLoader;
import com.grucis.dev.mapper.dictionary.MapDictionaryMapper;
import com.grucis.dev.model.dictionary.map.MapDictionary;
import com.grucis.dev.model.dictionary.map.MapEntry;
import com.grucis.dev.model.raw.atomic.LS2Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class DictionaryModelService {

  private static final Logger LOG = LoggerFactory.getLogger(DictionaryModelService.class);

  @Autowired
  private ModelLoader modelLoader;
  @Autowired
  private MapDictionaryMapper mapDictionaryMapper;
  @Autowired
  private MapDictionaryExporter mapDictionaryExporter;
  @Autowired
  private RawModelService rawModelService;

  private Map<Integer, MapEntry> mapDictionary;

  private MapDictionary prepareMapDictionary(boolean refresh) {
    MapDictionary ret;
    if(refresh || (ret = modelLoader.loadMapDictionary()) == null) {
      Collection<LS2Map> ls2Maps = rawModelService.getAllLS2Maps();
      ret = new MapDictionary();
      ret.setDictionary(mapDictionaryMapper.createDictionary(ls2Maps));
      try {
        mapDictionaryExporter.export(ret);
      } catch(Exception e) {
        LOG.error("Cannot export MapDictionary");
      }
    }
    mapDictionary = ret.getDictionary();
    return ret;
  }

  public Collection<MapEntry> getAllMapEntries() {
    if(mapDictionary == null) {
      prepareMapDictionary(false);
    }
    return mapDictionary.values();
  }

  public MapEntry getMapEntry(int id) {
    if(mapDictionary == null) {
      prepareMapDictionary(false);
    }
    return mapDictionary.get(id);
  }

}
