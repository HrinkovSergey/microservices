package com.home.user.repository;

import com.home.user.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void clearDatabase() {
        userRepository.deleteAll();
    }

    @Test
    void userRepository_all_are_ok_save() {
        String userId = null;
        String email = "email";
        String firstName = "firstName";
        String lastName = "lastName";
        Long companyId = 1L;
        Long locationId = 2L;
        User userToSave = createUser(userId, email, firstName, lastName, companyId, locationId);

        User resultUser = userRepository.save(userToSave);

        assertNotEquals(userId, resultUser.getId());
        assertEquals(email, resultUser.getEmail());
        assertEquals(firstName, resultUser.getFirstName());
        assertEquals(lastName, resultUser.getLastName());
        assertEquals(companyId, resultUser.getCompanyId());
        assertEquals(locationId, resultUser.getLocationId());
    }

    @Test
    void userRepository_all_are_ok_findById() {
        String userId = null;
        String email = "email";
        String firstName = "firstName";
        String lastName = "lastName";
        Long companyId = 1L;
        Long locationId = 2L;
        User userToSave = createUser(userId, email, firstName, lastName, companyId, locationId);
        userId = userRepository.save(userToSave).getId();

        Optional<User> optionalUser = userRepository.findById(userId);

        assertTrue(optionalUser.isPresent());
        User resultUser = optionalUser.get();
        assertEquals(userId, resultUser.getId());
        assertEquals(email, resultUser.getEmail());
        assertEquals(firstName, resultUser.getFirstName());
        assertEquals(lastName, resultUser.getLastName());
        assertEquals(companyId, resultUser.getCompanyId());
        assertEquals(locationId, resultUser.getLocationId());
    }

    private User createUser(String userId, String email, String firstName, String lastName, Long companyId, Long locationId) {
        User user = new User();
        user.setId(userId);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCompanyId(companyId);
        user.setLocationId(locationId);
        return user;
    }

}