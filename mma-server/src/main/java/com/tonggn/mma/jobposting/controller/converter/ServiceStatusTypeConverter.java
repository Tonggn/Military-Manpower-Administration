package com.tonggn.mma.jobposting.controller.converter;

import com.tonggn.mma.jobposting.command.domain.ServiceStatusType;
import org.springframework.core.convert.converter.Converter;

public class ServiceStatusTypeConverter implements Converter<String, ServiceStatusType> {

  @Override
  public ServiceStatusType convert(final String code) {
    return ServiceStatusType.of(code);
  }
}
