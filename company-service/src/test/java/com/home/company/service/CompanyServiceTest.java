package com.home.company.service;

import com.home.company.domain.Company;
import com.home.company.dto.CompanyDto;
import com.home.company.dto.LocationDto;
import com.home.company.exception.CompanyException;
import com.home.company.helper.CompanyHelper;
import com.home.company.helper.LocationHelper;
import com.home.company.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@Import({LocationHelper.class, CompanyHelper.class})
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.properties"})
class CompanyServiceTest {
    @Autowired
    private CompanyService companyService;
    @MockBean
    private CompanyRepository companyRepository;
    @Autowired
    private LocationHelper locationHelper;
    @Autowired
    private CompanyHelper companyHelper;
    @MockBean
    private RestTemplate restTemplate;
    @Value("${spring.microservice.location.base-url}")
    private String locationUrl;

    @Test
    void CompanyService_all_are_ok_saveCompany() {
        Long companyId = 1L;
        String companyName = "companyName";
        Long locationId = 2L;
        Company companyToSave = companyHelper.createCompany(null, companyName, locationId);
        Company savedCompany = companyHelper.createCompany(companyId, companyName, locationId);
        LocationDto locationDto = locationHelper.createLocationDto(locationId, "country", "city");
        doReturn(savedCompany).when(companyRepository).save(companyToSave);
        doReturn(locationDto).when(restTemplate).getForObject(locationUrl + locationId, LocationDto.class);

        CompanyDto companyDto = companyService.saveCompany(companyToSave);

        LocationDto resultLocationDto = companyDto.getLocation();
        assertEquals(companyId, companyDto.getId());
        assertEquals(companyName, companyDto.getCompanyName());
        assertEquals(locationDto.getId(), resultLocationDto.getId());
        assertEquals(locationDto.getLocationCountry(), resultLocationDto.getLocationCountry());
        assertEquals(locationDto.getLocationCity(), resultLocationDto.getLocationCity());
    }

    @Test
    void CompanyService_all_are_ok_findCompanyById() {
        Long companyId = 1L;
        String companyName = "companyName";
        Long locationId = 2L;
        Optional<Company> company = Optional.ofNullable(companyHelper.createCompany(companyId, companyName, locationId));
        LocationDto locationDto = locationHelper.createLocationDto(locationId, "country", "city");
        doReturn(company).when(companyRepository).findById(companyId);
        doReturn(locationDto).when(restTemplate).getForObject(locationUrl + locationId, LocationDto.class);

        CompanyDto companyDto = companyService.findCompanyById(companyId);

        LocationDto resultLocationDto = companyDto.getLocation();
        assertEquals(companyId, companyDto.getId());
        assertEquals(companyName, companyDto.getCompanyName());
        assertEquals(locationDto.getId(), resultLocationDto.getId());
        assertEquals(locationDto.getLocationCountry(), resultLocationDto.getLocationCountry());
        assertEquals(locationDto.getLocationCity(), resultLocationDto.getLocationCity());
    }

    @Test
    void CompanyService_company_empty_findCompanyById() {
        Long companyId = 1L;
        Optional<Company> company = Optional.empty();
        doReturn(company).when(companyRepository).findById(companyId);

        assertThrows(CompanyException.class, () -> companyService.findCompanyById(companyId));
    }
}

