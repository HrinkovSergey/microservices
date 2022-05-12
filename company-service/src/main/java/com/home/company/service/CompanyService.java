package com.home.company.service;

import com.home.company.domain.Company;
import com.home.company.dto.CompanyDto;

public interface CompanyService {
    CompanyDto saveCompany(Company company);

    CompanyDto findCompanyById(Long companyId);
}
