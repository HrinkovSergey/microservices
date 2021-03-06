package com.home.location.controller;

import com.home.location.domain.Location;
import com.home.location.dto.LocationDto;
import com.home.location.mapper.LocationMapper;
import com.home.location.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations")
@Slf4j
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/")
    public LocationDto saveLocation(@RequestBody LocationDto locationDto) {
        Location location = locationService.saveLocation(LocationMapper.INSTANCE.toEntity(locationDto));
        return LocationMapper.INSTANCE.toDto(location);
    }

    @GetMapping("/{id}")
    public LocationDto findLocationById(@PathVariable("id") Long locationId) {
        Location returnLocation = locationService.findLocationById(locationId);
        return LocationMapper.INSTANCE.toDto(returnLocation);
    }
}
