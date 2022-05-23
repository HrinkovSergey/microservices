package com.home.location.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.location.domain.Location;
import com.home.location.dto.LocationDto;
import com.home.location.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.config.location = classpath:application-integrationtest.yml"})
class LocationControllerTest {
    private static final String REQUEST_MAPPING = "/locations";
    private static final String POST_MAPPING = "/";
    private static final String GET_MAPPING = "/";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LocationRepository repository;

    @Test
    void saveLocation() throws Exception {
        Long locationId = null;
        String country = "test_country";
        String city = "test_city";
        LocationDto locationDto = createLocationDto(locationId, country, city);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .post(REQUEST_MAPPING + POST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDto)))
                .andDo(print());

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        LocationDto resultLocationDto = objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsString(), LocationDto.class);
        assertEquals(city, resultLocationDto.getLocationCity());
        assertEquals(country, resultLocationDto.getLocationCountry());
        assertNotEquals(locationId, resultLocationDto.getId());
    }

    @Test
    void findLocationById() throws Exception {
        Long locationId = null;
        String country = "test_country";
        String city = "test_city";
        Location location = createLocation(locationId, country, city);
        locationId = repository.save(location).getId();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .get(REQUEST_MAPPING + GET_MAPPING + locationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        LocationDto resultLocationDto = objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsString(), LocationDto.class);
        assertEquals(city, resultLocationDto.getLocationCity());
        assertEquals(country, resultLocationDto.getLocationCountry());
        assertEquals(locationId, resultLocationDto.getId());
    }

    private Location createLocation(Long locationId, String country, String city) {
        Location location = new Location();
        location.setId(locationId);
        location.setLocationCountry(country);
        location.setLocationCity(city);
        return location;
    }

    private LocationDto createLocationDto(Long locationId, String country, String city) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(locationId);
        locationDto.setLocationCountry(country);
        locationDto.setLocationCity(city);
        return locationDto;
    }
}