package com.tonggn.mma.jobposting.controller.converter;

import com.tonggn.mma.jobposting.command.domain.BusinessType;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.convert.converter.Converter;

public class BusinessTypesConverter implements Converter<String, List<BusinessType>> {

  private static final String DELIMITER = ",";

  @Override
  public List<BusinessType> convert(final String code) {
    final String[] codes = code.split(DELIMITER);
    return Arrays.stream(codes)
        .map(BusinessType::of)
        .toList();
  }
}
