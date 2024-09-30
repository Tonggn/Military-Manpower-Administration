package com.tonggn.mma.config;

import com.tonggn.mma.jobposting.controller.converter.AgentTypeConverter;
import com.tonggn.mma.jobposting.controller.converter.BusinessTypesConverter;
import com.tonggn.mma.jobposting.controller.converter.ServiceStatusTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(final ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("pages/index");
  }

  @Override
  public void addFormatters(final FormatterRegistry registry) {
    registry.addConverter(new BusinessTypesConverter());
    registry.addConverter(new ServiceStatusTypeConverter());
    registry.addConverter(new AgentTypeConverter());
  }
}
