package com.home.location.service;

import com.home.location.domain.Location;
import com.home.location.exception.LocationException;
import com.home.location.helper.LocationHelper;
import com.home.location.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@Import(LocationHelper.class)
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.properties"})
class LocationServiceImplTest {
    @MockBean
    private LocationRepository locationRepository;

    @Autowired
    private LocationHelper locationHelper;
    @Autowired
    private LocationService locationService;

    @Test
    void saveLocation() {
        Long locationId = 1L;
        String country = "country";
        String city = "city";
        Location locationToSave = locationHelper.createLocation(null, country, city);
        Location location = locationHelper.createLocation(locationId, country, city);
        doReturn(location).when(locationRepository).save(locationToSave);

        Location resultLocation = locationService.saveLocation(locationToSave);

        assertEquals(locationId, resultLocation.getId());
        assertEquals(country, resultLocation.getLocationCountry());
        assertEquals(city, resultLocation.getLocationCity());
    }

    @Test
    void findLocationById() {
        Long locationId = 1L;
        String country = "country";
        String city = "city";
        Optional<Location> optionalLocation = Optional.ofNullable(locationHelper.createLocation(locationId, country, city));
        doReturn(optionalLocation).when(locationRepository).findById(locationId);

        Location resultLocation = locationService.findLocationById(locationId);

        assertEquals(locationId, resultLocation.getId());
        assertEquals(country, resultLocation.getLocationCountry());
        assertEquals(city, resultLocation.getLocationCity());
    }

    @Test
    void findLocationById_thrown_exception() {
        Long locationId = 1L;
        doReturn(Optional.empty()).when(locationRepository).findById(locationId);

        assertThrows(LocationException.class, () -> locationService.findLocationById(locationId));
    }
}