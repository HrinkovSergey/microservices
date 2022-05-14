package com.home.location.service;

import com.home.location.domain.Location;
import com.home.location.exception.LocationException;
import com.home.location.repository.LocationRepository;
import com.home.logger.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@Log
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location findLocationById(Long locationId) {
        Optional<Location> location = locationRepository.findById(locationId);
        if (location.isEmpty()) {
            log.warn("There is no such location");
            throw new LocationException("There is no such location");
        }
        return location.get();
    }
}
