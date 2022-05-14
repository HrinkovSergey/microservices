package com.home.user.service;

import com.home.logger.annotation.Log;
import com.home.user.domain.User;
import com.home.user.dto.CompanyDto;
import com.home.user.dto.LocationDto;
import com.home.user.dto.UserDto;
import com.home.user.exception.UserException;
import com.home.user.mapper.UserMapper;
import com.home.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
@Log
public class UserServiceImpl implements UserService{
    @Value("${spring.microservice.company.base-url}")
    private String companyUrl;
    @Value("${spring.microservice.location.base-url}")
    private String locationUrl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserDto saveUser(User user) {
        log.info("Inside saveUser of UserService");
        User returnUser = userRepository.save(user);
        return getUserDto(returnUser);
    }

    @Override
    public UserDto findUserById(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserException("There is no such user");
        }
        return getUserDto(optionalUser.get());
    }

    private UserDto getUserDto(User user) {
        CompanyDto companyDto;
        LocationDto locationDto;
        companyDto = restTemplate.getForObject(
                    companyUrl + user.getCompanyId(), CompanyDto.class);
            locationDto = restTemplate.getForObject(
                    locationUrl + user.getLocationId(), LocationDto.class);
        UserDto userDto = UserMapper.INSTANCE.toDto(user);
        userDto.setCompany(companyDto);
        userDto.setLocation(locationDto);
        return userDto;
    }
}