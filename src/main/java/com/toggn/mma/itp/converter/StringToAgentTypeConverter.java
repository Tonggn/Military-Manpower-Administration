package com.toggn.mma.itp.converter;

import com.toggn.mma.itp.notice.domain.AgentType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class StringToAgentTypeConverter implements Converter<String, AgentType> {

    @Override
    public AgentType convert(@Nullable final String code) {
        return AgentType.from(code);
    }
}
