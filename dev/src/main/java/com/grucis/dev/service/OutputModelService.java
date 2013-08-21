package com.grucis.dev.service;

import java.util.ArrayList;
import java.util.Collection;

import com.grucis.dev.mapper.output.OffsetImageMapper;
import com.grucis.dev.mapper.output.SaMapMapper;
import com.grucis.dev.mapper.output.SpriteAnimationMapper;
import com.grucis.dev.model.dictionary.map.MapEntry;
import com.grucis.dev.model.output.AnimationMap;
import com.grucis.dev.model.output.OffsetImage;
import com.grucis.dev.model.output.SaMap;
import com.grucis.dev.model.raw.atomic.LS2Map;
import com.grucis.dev.model.raw.index.Adrn;
import com.grucis.dev.model.raw.index.SprAdrn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class OutputModelService {

  @Autowired
  private RawModelService rawModelService;
  @Autowired
  private DictionaryModelService dictionaryModelService;
  @Autowired
  private OffsetImageMapper offsetImageMapper;
  @Autowired
  private SpriteAnimationMapper spriteAnimationMapper;
  @Autowired
  private SaMapMapper saMapMapper;

  public OffsetImage getOffsetImage(int id) {
    Adrn adrn = rawModelService.getAdrn(id);
    return offsetImageMapper.map(adrn);
  }

  public AnimationMap getAnimationMap(int id) {
    SprAdrn sprAdrn = rawModelService.getSprAdrn(id);
    return spriteAnimationMapper.map(sprAdrn);
  }

  public SaMap getSaMap(int id) {
    MapEntry mapEntry = dictionaryModelService.getMapEntry(id);
    LS2Map ls2Map = rawModelService.getLS2Map(mapEntry.getPath());
    return saMapMapper.map(ls2Map);
  }

  public Collection<SaMap> getAllSaMaps() {
    Collection<SaMap> ret = new ArrayList<SaMap>();
    Collection<LS2Map> ls2Maps = rawModelService.getAllLS2Maps();
    for(LS2Map ls2Map : ls2Maps) {
      ret.add(saMapMapper.map(ls2Map));
    }

    return ret;
  }
}
