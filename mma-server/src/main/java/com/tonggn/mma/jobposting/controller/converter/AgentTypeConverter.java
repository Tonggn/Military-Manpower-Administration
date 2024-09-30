package com.tonggn.mma.jobposting.controller.converter;

import com.tonggn.mma.jobposting.command.domain.AgentType;
import org.springframework.core.convert.converter.Converter;

public class AgentTypeConverter implements Converter<String, AgentType> {

  @Override
  public AgentType convert(final String code) {
    return AgentType.of(code);
  }
}
