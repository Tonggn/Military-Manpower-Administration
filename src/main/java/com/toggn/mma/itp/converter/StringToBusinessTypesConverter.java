package com.toggn.mma.itp.converter;

import com.toggn.mma.itp.enterprise.domain.BusinessType;
import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;
import java.util.List;

public class StringToBusinessTypesConverter implements Converter<String, List<BusinessType>> {

    private static final String CODE_DELIMITER = ",";

    @Override
    public List<BusinessType> convert(final String codesString) {
        final String[] codes = codesString.split(CODE_DELIMITER);
        return Arrays.stream(codes)
                .map(BusinessType::from)
                .toList();
    }
}
