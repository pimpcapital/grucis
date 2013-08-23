package com.grucis.dev.io;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.grucis.dev.exporter.AnimationExporter;
import com.grucis.dev.exporter.BitmapExporter;
import com.grucis.dev.exporter.MapExporter;
import com.grucis.dev.model.dictionary.map.MapEntry;
import com.grucis.dev.model.export.animation.AnimationSpriteSheet;
import com.grucis.dev.model.export.bitmap.OffsetBitmap;
import com.grucis.dev.model.export.map.TileMap;
import com.grucis.dev.model.progress.ExportError;
import com.grucis.dev.model.progress.ExportProgress;
import com.grucis.dev.model.raw.index.Adrn;
import com.grucis.dev.model.raw.index.SprAdrn;
import com.grucis.dev.service.DictionaryModelService;
import com.grucis.dev.service.ExportModelService;
import com.grucis.dev.service.RawModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ExportManager {
  private static final Logger LOG = LoggerFactory.getLogger(ExportManager.class);

  @Autowired
  private RawModelService rawModelService;
  @Autowired
  private ExportModelService exportModelService;
  @Autowired
  private DictionaryModelService dictionaryModelService;
  @Autowired
  private BitmapExporter bitmapExporter;
  @Autowired
  private AnimationExporter animationExporter;
  @Autowired
  private MapExporter mapExporter;

  public void exportBitmaps(ExportProgress progress) {
    List<ExportError> errors = new ArrayList<ExportError>();
    progress.setErrors(errors);
    Collection<Adrn> adrns = rawModelService.getAllAdrns();
    int total = adrns.size();
    progress.setTotal(total);
    int count = 0;
    for(Adrn adrn : adrns) {
      int id = adrn.getId();
      OffsetBitmap offsetBitmap = exportModelService.getOffsetBitmap(id, false);
      String current = Integer.toString(offsetBitmap.getId());
      progress.setCurrent(current);
      try {
        bitmapExporter.export(offsetBitmap);
      } catch(Exception e) {
        errors.add(new ExportError(current, e.getMessage()));
      }
      count++;
      progress.setProgress(count);
      progress.setPercent(((double)count) / total);
    }
    progress.setFinished(true);
  }

  public void exportAnimations(ExportProgress progress) {
    List<ExportError> errors = new ArrayList<ExportError>();
    progress.setErrors(errors);
    Collection<SprAdrn> spradrns = rawModelService.getAllSprAdrns();
    int total = spradrns.size();
    progress.setTotal(total);
    int count = 0;
    for(SprAdrn sprAdrn : spradrns) {
      int id = sprAdrn.getId();
      AnimationSpriteSheet animationSpriteSheet = exportModelService.getAnimationSpriteSheet(id, false);
      String current = Integer.toString(animationSpriteSheet.getId());
      progress.setCurrent(current);
      try {
        animationExporter.export(animationSpriteSheet);
      } catch(Exception e) {
        errors.add(new ExportError(current, e.getMessage()));
      }
      count++;
      progress.setProgress(count);
      progress.setPercent(((double)count) / total);
    }
    progress.setFinished(true);
  }

  public void exportMaps(ExportProgress progress) {
    List<ExportError> errors = new ArrayList<ExportError>();
    progress.setErrors(errors);
    Collection<MapEntry> mapEntries = dictionaryModelService.getAllMapEntries();
    int total = mapEntries.size();
    progress.setTotal(total);
    int count = 0;
    for(MapEntry mapEntry : mapEntries) {
      int id = mapEntry.getId();
      TileMap tileMap = exportModelService.getTileMap(id, false);
      String current = Integer.toString(tileMap.getId());
      progress.setCurrent(current);
      try {
        mapExporter.export(tileMap);
      } catch(Exception e) {
        errors.add(new ExportError(current, e.getMessage()));
      }
      count++;
      progress.setProgress(count);
      progress.setPercent(((double)count) / total);
    }
    progress.setFinished(true);
  }
}
