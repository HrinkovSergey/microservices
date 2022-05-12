package com.home.company.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CompanyForCreateDto {
    private String companyName;
    private String locationId;
}
