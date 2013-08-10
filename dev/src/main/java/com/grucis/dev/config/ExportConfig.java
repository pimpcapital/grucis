package com.grucis.dev.config;

import com.grucis.dev.model.setting.BitmapExportSetting;
import com.grucis.dev.model.setting.SpriteExportSetting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/export.properties")
public class ExportConfig {

  @Value("${bitmap.path}")
  private String bitmapPath;
  @Value("${bitmap.format}")
  private String bitmapFormat;
  @Value("${sprite.path}")
  private String spritePath;
  @Value("${sprite.format}")
  private String spriteFormat;

  @Bean
  public BitmapExportSetting bitmapExportSetting() {
    BitmapExportSetting ret = new BitmapExportSetting();
    ret.setPath(bitmapPath);
    ret.setFormat(bitmapFormat);
    return ret;
  }

  @Bean
  public SpriteExportSetting spriteExportSetting() {
    SpriteExportSetting ret = new SpriteExportSetting();
    ret.setPath(spritePath);
    ret.setFormat(spriteFormat);
    return ret;
  }

}
