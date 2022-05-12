package com.home.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CompanyDto {
    private Long id;
    private String companyName;
    private LocationDto location;
}
