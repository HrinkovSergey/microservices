package com.home.location.service;

import com.home.location.domain.Location;

public interface LocationService {
    Location saveLocation(Location location);

    Location findLocationById(String locationId);
}
