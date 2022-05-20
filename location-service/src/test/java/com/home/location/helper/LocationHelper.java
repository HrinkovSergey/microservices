package com.home.location.helper;

import com.home.location.domain.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationHelper {

    public Location createLocation(Long locationId, String country, String city) {
        Location location = new Location();
        location.setId(locationId);
        location.setLocationCountry(country);
        location.setLocationCity(city);
        return location;
    }
}
