package com.home.company.controller;

import com.home.company.dto.CompanyDto;
import com.home.company.dto.CompanyForCreateDto;
import com.home.company.dtogetter.exception.LocationServiceException;
import com.home.company.exception.CompanyException;
import com.home.company.mapping.CompanyMapper;
import com.home.company.service.CompanyService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/companies")
@Slf4j
public class CompanyController {

    private final List<Class<? extends RuntimeException>> serviceExceptions = Arrays.asList(
            LocationServiceException.class, CompanyException.class);

    @Autowired
    private CompanyService companyService;

    @PostMapping("/")
    public CompanyDto saveCompany(@RequestBody CompanyForCreateDto companyDto) {
        return companyService.saveCompany(CompanyMapper.INSTANCE.toEntity(companyDto));
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "companyService", fallbackMethod = "serviceFallback")
    public CompanyDto findCompanyById(@PathVariable("id") Long companyId) {
        return companyService.findCompanyById(companyId);
    }

    public CompanyDto serviceFallback(Exception exception) throws Exception {
        for (Class<? extends RuntimeException> serviceException : serviceExceptions) {
            if (serviceException == exception.getClass()) {
                throw exception;
            }
        }
        throw new NoFallbackAvailableException(exception.getMessage(), exception);
    }
}
