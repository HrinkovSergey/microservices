package com.home.gateway.controller;

import com.home.gateway.dto.ClientMessageDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {

    @GetMapping("/userServiceFallBack")
    public ClientMessageDto userServiceFallBackMethod() {
        return new ClientMessageDto("User Service is taking longer than Expected. Please try again later");
    }

    @GetMapping("/companyServiceFallBack")
    public ClientMessageDto departmentServiceFallBackMethod() {
        return new ClientMessageDto("Company Service is taking longer than Expected. Please try again later");
    }

    @GetMapping("/locationServiceFallBack")
    public ClientMessageDto locationServiceFallBackMethod() {
        return new ClientMessageDto("Location Service is taking longer than Expected. Please try again later");
    }
}