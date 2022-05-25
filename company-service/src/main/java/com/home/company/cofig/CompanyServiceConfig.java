package com.home.company.cofig;

import com.home.company.dto.LocationDto;
import com.home.company.dtogetter.DtoGetter;
import com.home.company.dtogetter.LocationDtoGetterImpl;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
public class CompanyServiceConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DtoGetter<LocationDto, Long> locationDtoGetter() {
        return new LocationDtoGetterImpl();
    }
}
