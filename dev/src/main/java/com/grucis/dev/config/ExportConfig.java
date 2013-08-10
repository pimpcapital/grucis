package com.grucis.dev.config;

import com.grucis.dev.model.setting.AnimationExportSetting;
import com.grucis.dev.model.setting.BitmapExportSetting;
import com.grucis.dev.model.setting.MapExportSetting;
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
  @Value("${map.path}")
  private String mapPath;


  @Bean
  public BitmapExportSetting bitmapExportSetting() {
    BitmapExportSetting ret = new BitmapExportSetting();
    ret.setPath(bitmapPath);
    ret.setFormat(bitmapFormat);
    return ret;
  }

  @Bean
  public AnimationExportSetting animationExportSetting() {
    AnimationExportSetting ret = new AnimationExportSetting();
    ret.setPath(animationPath);
    ret.setFormat(animationFormat);
    return ret;
  }

  @Bean
  public MapExportSetting mapExportSetting() {
    MapExportSetting ret = new MapExportSetting();
    ret.setPath(mapPath);
    return ret;
  }


}
