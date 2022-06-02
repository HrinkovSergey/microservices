package com.home.user.dtogetter;

import com.home.user.dto.CompanyDto;
import org.springframework.stereotype.Component;

@Component
public class CompanyDtoGetterImpl extends AbstractDtoGetter<CompanyDto, Long> {

    public CompanyDtoGetterImpl() {
        setResponseType(CompanyDto.class);
    }

    @Override
    String getParameters(Long companyId) {
        return String.valueOf(companyId);
    }
}
