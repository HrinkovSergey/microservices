package com.home.company.cofig;

import com.home.company.dto.LocationDto;
import com.home.company.dtogetter.DtoGetter;
import com.home.company.dtogetter.LocationDtoGetterImpl;
import com.home.company.dtogetter.exception.LocationResponseErrorHandler;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
public class CompanyServiceConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(locationResponseErrorHandler());
        return restTemplate;
    }

    @Bean
    public ResponseErrorHandler locationResponseErrorHandler() {
        return new LocationResponseErrorHandler();
    }

    @Bean
    public DtoGetter<LocationDto, Long> locationDtoGetter() {
        return new LocationDtoGetterImpl();
    }
}
