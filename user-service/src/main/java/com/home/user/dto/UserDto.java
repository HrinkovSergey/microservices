package com.home.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private CompanyDto company;
    private LocationDto location;
}
