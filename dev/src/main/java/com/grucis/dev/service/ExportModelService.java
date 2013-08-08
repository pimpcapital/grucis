package com.grucis.dev.service;

import com.grucis.dev.exporter.BitmapExporter;
import com.grucis.dev.io.ModelLoader;
import com.grucis.dev.mapper.export.OffsetBitmapMapper;
import com.grucis.dev.model.export.bitmap.OffsetBitmap;
import com.grucis.dev.model.output.OffsetImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportModelService {

  private static final Logger LOG = LoggerFactory.getLogger(ExportModelService.class);

  @Autowired
  private OutputModelService outputModelService;
  @Autowired
  private OffsetBitmapMapper offsetBitmapMapper;
  @Autowired
  private BitmapExporter bitmapExporter;
  @Autowired
  private ModelLoader modelLoader;

  public OffsetBitmap getOffsetBitmap(int id) {
    OffsetBitmap ret = modelLoader.loadOffsetBitmap(id);

    if(ret == null) {
      OffsetImage image = outputModelService.getOffsetImage(id);
      ret = offsetBitmapMapper.map(image);
      try {
        bitmapExporter.export(ret);
      } catch(Exception e) {
        LOG.error("Cannot export OffsetBitmap #{}", id);
      }
    }

    return ret;
  }
}
