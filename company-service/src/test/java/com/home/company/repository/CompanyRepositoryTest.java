package com.home.company.repository;

import com.home.company.domain.Company;
import com.home.company.helper.CompanyHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@Import(CompanyHelper.class)
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyHelper companyHelper;

    @AfterEach
    void clearDatabase() {
        companyRepository.deleteAll();
    }

    @Test
    void companyRepository_save_all_are_ok() {
        Long companyId = null;
        String companyName = "companyName";
        Long locationId = 1L;
        Company companyToSave = companyHelper.createCompany(companyId, companyName, locationId);

        Company company = companyRepository.save(companyToSave);

        assertNotEquals(companyId, company.getId());
        assertEquals(companyName, company.getCompanyName());
        assertEquals(locationId, company.getLocationId());
    }

    @Test
    void companyRepository_findCompanyById_all_are_ok() {
        Long companyId = null;
        String companyName = "testName";
        Long locationId = 2L;
        Company company = companyHelper.createCompany(companyId, companyName, locationId);
        Company savedCompany = companyRepository.save(company);
        companyId = savedCompany.getId();

        Optional<Company> optionalCompany = companyRepository.findById(companyId);

        assertTrue(optionalCompany.isPresent());
        Company companyById = optionalCompany.get();
        assertEquals(companyId, companyById.getId());
        assertEquals(companyName, companyById.getCompanyName());
        assertEquals(locationId, companyById.getLocationId());
    }
}
