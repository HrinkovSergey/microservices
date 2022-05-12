package com.home.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserForCreatingDto {
    private String firstName;
    private String lastName;
    private String email;
    private Long companyId;
    private String locationId;
}
