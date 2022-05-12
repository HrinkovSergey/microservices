package com.home.company.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LocationDto {
    private String id;
    private String locationCountry;
    private String locationCity;
}
