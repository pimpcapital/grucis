package com.grucis.dev.config;

import com.grucis.dev.DevModuleMarker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ResourcesConfig.class)
@ComponentScan(basePackageClasses = DevModuleMarker.class)
public final class DevConfig {
}
