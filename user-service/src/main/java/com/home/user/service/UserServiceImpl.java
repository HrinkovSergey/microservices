package com.home.user.service;

import com.home.logger.annotation.Log;
import com.home.user.domain.User;
import com.home.user.dto.CompanyDto;
import com.home.user.dto.LocationDto;
import com.home.user.dto.UserDto;
import com.home.user.dtogetter.DtoGetter;
import com.home.user.exception.UserException;
import com.home.user.mapper.UserMapper;
import com.home.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@Log
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoGetter<LocationDto, Long> locationDtoGetter;

    @Autowired
    private DtoGetter<CompanyDto, Long> companyDtoGetter;

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
        CompanyDto companyDto = companyDtoGetter.getDtoFromExternalResource(user.getCompanyId());
        LocationDto locationDto = locationDtoGetter.getDtoFromExternalResource(user.getLocationId());
        UserDto userDto = UserMapper.INSTANCE.toDto(user);
        userDto.setCompany(companyDto);
        userDto.setLocation(locationDto);
        return userDto;
    }
}