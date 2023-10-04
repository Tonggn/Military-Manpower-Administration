package com.toggn.mma.itp.converter;

import com.toggn.mma.itp.notice.domain.ServiceStatusType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class StringToServiceStatusTypeConverter implements Converter<String, ServiceStatusType> {
    @Override
    public ServiceStatusType convert(@Nullable final String source) {
        return ServiceStatusType.from(source);
    }
}
