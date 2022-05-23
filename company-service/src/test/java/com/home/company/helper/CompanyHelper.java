package com.home.company.helper;

import com.home.company.domain.Company;
import com.home.company.dto.CompanyForCreateDto;
import org.springframework.stereotype.Component;

@Component
public class CompanyHelper {
    public Company createCompany(Long companyId, String companyName, Long locationId) {
        Company company = new Company();
        company.setCompanyName(companyName);
        company.setId(companyId);
        company.setLocationId(locationId);
        return company;
    }

    public CompanyForCreateDto createCompanyForCreateDto(String companyName, Long locationId) {
        CompanyForCreateDto companyForCreateDto = new CompanyForCreateDto();
        companyForCreateDto.setCompanyName(companyName);
        companyForCreateDto.setLocationId(locationId);
        return companyForCreateDto;
    }
}
