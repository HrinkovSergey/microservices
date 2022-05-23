package com.home.location.repository;

import com.home.location.domain.Location;
import com.home.location.helper.LocationHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@Import(LocationHelper.class)
class LocationRepositoryTest {
    @Autowired
    private LocationHelper locationHelper;
    @Autowired
    private LocationRepository locationRepository;

    @AfterEach
    void clearDatabase() {
        locationRepository.deleteAll();
    }

    @Test
    void locationRepository_all_are_ok_save() {
        Long locationId = null;
        String country = "country";
        String city = "city";
        Location locationToSave = locationHelper.createLocation(locationId, country, city);

        Location location = locationRepository.save(locationToSave);

        assertNotEquals(locationId, location.getId());
        assertEquals(country, location.getLocationCountry());
        assertEquals(city, location.getLocationCity());
    }

    @Test
    void locationRepository_all_are_ok_findById() {
        Long locationId = null;
        String country = "country";
        String city = "city";
        Location location = locationHelper.createLocation(locationId, country, city);
        locationId = locationRepository.save(location).getId();

        Optional<Location> optionalLocation = locationRepository.findById(locationId);

        assertTrue(optionalLocation.isPresent());
        Location result = optionalLocation.get();
        assertEquals(locationId, result.getId());
        assertEquals(country, result.getLocationCountry());
        assertEquals(city, result.getLocationCity());
    }
}