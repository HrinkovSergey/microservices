package com.home.company.controller;

import com.home.company.bpp.annotation.Log;
import com.home.company.dto.CompanyDto;
import com.home.company.dto.CompanyForCreateDto;
import com.home.company.mapping.CompanyMapper;
import com.home.company.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
@Slf4j
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/")
    public CompanyDto saveCompany(@RequestBody CompanyForCreateDto companyDto) {
        return companyService.saveCompany(CompanyMapper.INSTANCE.toEntity(companyDto));
    }

    @GetMapping("/{id}")
    public CompanyDto findDepartmentById(@PathVariable("id") Long departmentId) {
        return companyService.findCompanyById(departmentId);
    }

}
