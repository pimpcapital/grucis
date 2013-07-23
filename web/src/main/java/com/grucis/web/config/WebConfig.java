package com.grucis.web.config;

import com.grucis.web.WebModuleMarker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = WebModuleMarker.class)
public class WebConfig {

}
