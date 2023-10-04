package com.toggn.mma.config;

import com.toggn.mma.itp.converter.StringToAgentTypeConverter;
import com.toggn.mma.itp.converter.StringToBusinessTypesConverter;
import com.toggn.mma.itp.converter.StringToServiceStatusTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new StringToServiceStatusTypeConverter());
        registry.addConverter(new StringToAgentTypeConverter());
        registry.addConverter(new StringToBusinessTypesConverter());
    }
}
