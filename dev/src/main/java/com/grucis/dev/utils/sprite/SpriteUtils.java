package com.grucis.dev.utils.sprite;

import java.awt.image.BufferedImage;
import java.util.Map;

public final class SpriteUtils {

  public static boolean intersects(RectanglePlacement rp1, RectanglePlacement rp2) {
    int tw = rp1.getWidth();
    int th = rp1.getHeight();
    int rw = rp2.getWidth();
    int rh = rp2.getHeight();
    if(rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) return false;
    int tx = rp1.getX();
    int ty = rp1.getY();
    int rx = rp2.getX();
    int ry = rp2.getY();
    rw += rx;
    rh += ry;
    tw += tx;
    th += ty;
    return ((rw < rx || rw > tx) &&
              (rh < ry || rh > ty) &&
              (tw < tx || tw > rx) &&
              (th < ty || th > ry));
  }

  public static RectangleImagePlaceholder getPlaceHolder(BufferedImage image, int id) {
    return new RectangleImagePlaceholder(id, image.getWidth(), image.getHeight());
  }

  public static BufferedImage packImages(Map<Integer, BufferedImage> images, Map<Integer, RectanglePlacement> placements, int width, int height) {
    BufferedImage ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    for(Map.Entry<Integer, RectanglePlacement> entry : placements.entrySet()) {
      BufferedImage image = images.get(entry.getKey());
      RectanglePlacement placement = entry.getValue();
      for(int y = 0; y < image.getHeight(); y++) {
        for(int x = 0; x < image.getWidth(); x++) {
          int rgb = image.getRGB(x, y);
          ret.setRGB(x + placement.getX(), y + placement.getY(), rgb);
        }
      }
    }
    return ret;
  }
}
