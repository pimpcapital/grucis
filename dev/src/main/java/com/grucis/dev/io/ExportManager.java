package com.grucis.dev.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.grucis.dev.model.progress.BitmapExportProgress;
import com.grucis.dev.model.raw.index.Adrn;
import com.grucis.dev.model.setting.BitmapExportSetting;
import com.grucis.dev.service.OutputModelService;
import com.grucis.dev.service.RawModelService;
import com.grucis.dev.utils.image.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ExportManager {
  private static final Logger LOG = LoggerFactory.getLogger(ExportManager.class);

  @Autowired
  private BitmapExportSetting bitmapExportSetting;
  @Autowired
  private RawModelService rawModelService;
  @Autowired
  private OutputModelService outputModelService;

  public BitmapExportSetting getBitmapExportSetting() {
    return bitmapExportSetting;
  }


  public void exportBitmaps(BitmapExportProgress progress) {
    List<BitmapExportProgress.BitmapExportError> errors = new ArrayList<BitmapExportProgress.BitmapExportError>();
    progress.setErrors(errors);
    String path = bitmapExportSetting.getPath();
    File folder = new File(path);
    if(!folder.exists() && !folder.mkdirs() && !folder.isDirectory()) {
      errors.add(new BitmapExportProgress.BitmapExportError(path, "Cannot create folder"));
      progress.setFinished(true);
      return;
    }
    Collection<Adrn> adrns = rawModelService.getAllAdrns();
    String format = bitmapExportSetting.getFormat();
    int total = adrns.size();
    progress.setTotal(total);
    int count = 0;
    for(Adrn adrn : adrns) {
      int id = adrn.getId();
      String current = path + "\\" + id + "." + format;
      progress.setCurrent(current);
      try {
        BufferedImage image = outputModelService.getOffsetImage(id).getImage();
        byte[] bytes = ImageUtils.toBytes(image);
        File file = new File(current);
        if(file.exists()) file.delete();
        FileOutputStream output = new FileOutputStream(current);
        output.write(bytes);
        output.close();
      } catch(Exception e) {
        errors.add(new BitmapExportProgress.BitmapExportError(current, e.toString()));
      }
      count++;
      progress.setProgress(count);
      progress.setPercent(((double)count) / total);
    }
    progress.setFinished(true);
  }

}
