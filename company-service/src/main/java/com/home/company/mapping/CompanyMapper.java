package com.home.company.mapping;

import com.home.company.domain.Company;
import com.home.company.dto.CompanyDto;
import com.home.company.dto.CompanyForCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanyMapper {
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    @Mapping(target = "location", ignore = true)
    CompanyDto toDto(Company company);

    @Mapping(target = "id", ignore = true)
    Company toEntity(CompanyForCreateDto departmentForCreateDto);
}
