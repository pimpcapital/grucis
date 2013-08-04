package com.grucis.dev.config;

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

  @Bean
  public BitmapExportSetting bitmapFolder() {
    BitmapExportSetting ret = new BitmapExportSetting();
    ret.setPath(bitmapPath);
    ret.setFormat(bitmapFormat);
    return ret;
  }

}
