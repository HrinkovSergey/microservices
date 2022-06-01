package com.home.company.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.company.dto.CompanyDto;
import com.home.company.dto.CompanyForCreateDto;
import com.home.company.dto.LocationDto;
import com.home.company.dtogetter.DtoGetter;
import com.home.company.helper.CompanyHelper;
import com.home.company.helper.LocationHelper;
import com.home.company.repository.CompanyRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.config.location = classpath:application-integrationtest.yml"})
class CompanyControllerTest {
    private static final String REQUEST_MAPPING = "/companies";
    private static final String POST_MAPPING = "/";
    private static final String GET_MAPPING = "/";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyRepository companyRepository;
    @MockBean
    private DtoGetter<LocationDto, Long> locationDtoGetter;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${spring.microservice.location.base-url}")
    private String locationUrl;
    @Autowired
    private LocationHelper locationHelper;
    @Autowired
    private CompanyHelper companyHelper;

    @Test
    void saveCompany() throws Exception {
        String companyName = "testCompanyName";
        Long locationId = 2L;
        CompanyForCreateDto companyForCreateDto = companyHelper.createCompanyForCreateDto(companyName, locationId);
        LocationDto locationDto = locationHelper.createLocationDto(locationId, "country", "city");
        doReturn(locationDto).when(locationDtoGetter).getDtoFromExternalResource(locationId);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .post(REQUEST_MAPPING + POST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyForCreateDto)))
                .andDo(print());

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        CompanyDto resultCompanyDto = objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsString(), CompanyDto.class);
        LocationDto resultLocationDto = resultCompanyDto.getLocation();
        assertNotNull(resultCompanyDto.getId());
        assertEquals(companyName, resultCompanyDto.getCompanyName());
        assertEquals(locationDto.getId(), resultLocationDto.getId());
        assertEquals(locationDto.getLocationCountry(), resultLocationDto.getLocationCountry());
        assertEquals(locationDto.getLocationCity(), resultLocationDto.getLocationCity());
    }

    @Test
    void findCompanyById() throws Exception {
        Long companyId = null;
        String companyName = "testName";
        Long locationId = 3L;
        LocationDto locationDto = locationHelper.createLocationDto(locationId, "country", "city");
        doReturn(locationDto).when(locationDtoGetter).getDtoFromExternalResource(locationId);
        companyId = companyRepository.save(companyHelper.createCompany(companyId, companyName, locationId)).getId();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                        .get(REQUEST_MAPPING + GET_MAPPING + companyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        CompanyDto resultCompanyDto = objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsString(), CompanyDto.class);
        LocationDto resultLocationDto = resultCompanyDto.getLocation();
        assertEquals(companyId, resultCompanyDto.getId());
        assertEquals(companyName, resultCompanyDto.getCompanyName());
        assertEquals(locationDto.getId(), resultLocationDto.getId());
        assertEquals(locationDto.getLocationCountry(), resultLocationDto.getLocationCountry());
        assertEquals(locationDto.getLocationCity(), resultLocationDto.getLocationCity());
    }
}