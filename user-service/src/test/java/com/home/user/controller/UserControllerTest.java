package com.home.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.user.dto.CompanyDto;
import com.home.user.dto.LocationDto;
import com.home.user.dto.UserDto;
import com.home.user.dto.UserForCreatingDto;
import com.home.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.config.location = classpath:application-integrationtest.yml"})
class UserControllerTest {
    private static final String REQUEST_MAPPING = "/users";
    private static final String POST_MAPPING = "/";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${spring.microservice.location.base-url}")
    private String locationUrl;
    @Value("${spring.microservice.company.base-url}")
    private String companyUrl;

    @Test
    void saveUser() throws Exception {
        String userId = null;
        String email = "email";
        String firstName = "firstName";
        String lastName = "lastName";
        Long companyId = 2L;
        Long locationId = 2L;
        UserForCreatingDto userForCreatingDto = createUserForCreationDto(email, firstName, lastName, companyId, locationId);
        LocationDto locationDto = createLocationDto(locationId, "country", "city");
        CompanyDto  companyDto = createCompanyDto(companyId, "companyName", createLocationDto(3L, "testCountry", "testCity"));
        doReturn(locationDto).when(restTemplate).getForObject(locationUrl + locationId, LocationDto.class);
        doReturn(companyDto).when(restTemplate).getForObject(companyUrl + companyId, CompanyDto.class);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .post(REQUEST_MAPPING + POST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userForCreatingDto)))
                .andDo(print());

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        UserDto resultUserDto = objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsString(), UserDto.class);
        assertNotEquals(userId, resultUserDto.getId());
        assertEquals(email, resultUserDto.getEmail());
        assertEquals(firstName, resultUserDto.getFirstName());
        assertEquals(lastName, resultUserDto.getLastName());
        assertEquals(locationDto.getId(), resultUserDto.getLocation().getId());
        assertEquals(locationDto.getLocationCity(), resultUserDto.getLocation().getLocationCity());
        assertEquals(locationDto.getLocationCountry(), resultUserDto.getLocation().getLocationCountry());
        assertEquals(companyDto.getId(), resultUserDto.getCompany().getId());
        assertEquals(companyDto.getCompanyName(), resultUserDto.getCompany().getCompanyName());
        assertEquals(companyDto.getLocation().getId(), resultUserDto.getCompany().getLocation().getId());
        assertEquals(companyDto.getLocation().getLocationCity(), resultUserDto.getCompany().getLocation().getLocationCity());
        assertEquals(companyDto.getLocation().getLocationCountry(), resultUserDto.getCompany().getLocation().getLocationCountry());
    }

    private UserForCreatingDto createUserForCreationDto(String email, String firstName, String lastName, Long companyId, Long locationId) {
        UserForCreatingDto userForCreatingDto = new UserForCreatingDto();
        userForCreatingDto.setEmail(email);
        userForCreatingDto.setFirstName(firstName);
        userForCreatingDto.setLastName(lastName);
        userForCreatingDto.setLocationId(locationId);
        userForCreatingDto.setCompanyId(companyId);
        return userForCreatingDto;
    }

    private LocationDto createLocationDto(Long id, String locationCountry, String locationCity) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(id);
        locationDto.setLocationCountry(locationCountry);
        locationDto.setLocationCity(locationCity);
        return locationDto;
    }

    private CompanyDto createCompanyDto(Long companyId, String companyName, LocationDto locationDto) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(companyId);
        companyDto.setCompanyName(companyName);
        companyDto.setLocation(locationDto);
        return companyDto;
    }
}