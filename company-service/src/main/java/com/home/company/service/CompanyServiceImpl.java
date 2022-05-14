package com.home.company.service;

import com.home.company.domain.Company;
import com.home.company.dto.CompanyDto;
import com.home.company.dto.LocationDto;
import com.home.company.exception.CompanyException;
import com.home.company.mapping.CompanyMapper;
import com.home.company.repository.CompanyRepository;
import com.home.logger.annotation.LogMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    @Value("${spring.microservice.location.base-url}")
    private String locationUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CompanyRepository companyRepository;

    @LogMethod
    @Override
    public CompanyDto saveCompany(Company company) {
        Company returnCompany = companyRepository.save(company);
        return getCompanyDto(returnCompany);
    }

    @LogMethod
    @Override
    public CompanyDto findCompanyById(Long companyId) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if (companyOptional.isEmpty()) {
            throw new CompanyException("There is no company");
        }
        return getCompanyDto(companyOptional.get());
    }

    private CompanyDto getCompanyDto(Company company) {
        LocationDto locationDto = restTemplate.getForObject(
                locationUrl + company.getLocationId(), LocationDto.class);
        CompanyDto companyDto = CompanyMapper.INSTANCE.toDto(company);
        companyDto.setLocation(locationDto);
        return companyDto;
    }
}
