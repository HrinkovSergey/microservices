package com.home.company.helper;

import com.home.company.dto.LocationDto;
import org.springframework.stereotype.Component;

@Component
public class LocationHelper {
    public LocationDto createLocationDto(Long locationId, String country, String city) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(locationId);
        locationDto.setLocationCountry(country);
        locationDto.setLocationCity(city);
        return locationDto;
    }
}
