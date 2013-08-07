package com.grucis.dev.config;

import com.grucis.dev.model.setting.SpriteExportSetting;
import com.grucis.dev.model.setting.BitmapExportSetting;
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
  @Value("${animation.path}")
  private String animationPath;
  @Value("${animation.format}")
  private String animationFormat;

  @Bean
  public BitmapExportSetting bitmapExportSetting() {
    BitmapExportSetting ret = new BitmapExportSetting();
    ret.setPath(bitmapPath);
    ret.setFormat(bitmapFormat);
    return ret;
  }

  @Bean
  public SpriteExportSetting animationExportSetting() {
    SpriteExportSetting ret = new SpriteExportSetting();
    ret.setPath(animationPath);
    ret.setFormat(animationFormat);
    return ret;
  }

}
