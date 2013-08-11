package com.grucis.dev.mapper.export;

import com.grucis.dev.model.export.bitmap.BitmapIndex;
import com.grucis.dev.model.export.bitmap.OffsetBitmap;
import com.grucis.dev.model.output.OffsetImage;
import org.springframework.stereotype.Component;

@Component
public final class OffsetBitmapMapper extends ExportModelMapper<OffsetImage, OffsetBitmap> {
  @Override
  public OffsetBitmap map(OffsetImage source) {
    OffsetBitmap ret = new OffsetBitmap(source.getId());

    BitmapIndex index = new BitmapIndex();
    index.setRegX(-source.getxOffset());
    index.setRegY(-source.getyOffset());
    index.setEast(source.getEast());
    index.setSouth(source.getSouth());
    index.setObstructive(source.getObstructive());
    ret.setIndex(index);

    ret.setImage(source.getImage());

    return ret;
  }
}
