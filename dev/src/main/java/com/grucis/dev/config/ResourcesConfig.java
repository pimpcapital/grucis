package com.grucis.dev.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/resources.properties")
public final class ResourcesConfig {

  @Value("${data}")
  private String dataPath;
  @Value("${map}")
  private String mapPath;

  @Bean(name = "data")
  public File dataFolder() {
    return new File(dataPath);
  }

  @Bean(name = "map")
  public File mapFolder() {
    return new File(mapPath);
  }
}
