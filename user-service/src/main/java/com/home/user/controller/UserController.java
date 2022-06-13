package com.home.user.controller;

import com.home.user.domain.User;
import com.home.user.dto.UserDto;
import com.home.user.dto.UserForCreatingDto;
import com.home.user.dtogetter.exception.CompanyServiceException;
import com.home.user.dtogetter.exception.LocationServiceException;
import com.home.user.exception.UserException;
import com.home.user.mapper.UserMapper;
import com.home.user.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final List<Class<? extends RuntimeException>> serviceExceptions = Arrays.asList(
            LocationServiceException.class, CompanyServiceException.class, UserException.class);

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public UserDto saveUser(@RequestBody UserForCreatingDto userForCreatingDto) {
        User user = UserMapper.INSTANCE.toEntity(userForCreatingDto);
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "userService", fallbackMethod = "serviceFallback")
    public UserDto findUserById(@PathVariable("id") String userId) {
        return userService.findUserById(userId);
    }

    public UserDto serviceFallback(Exception exception) throws Exception {
        for (Class<? extends RuntimeException> serviceException : serviceExceptions) {
            if (serviceException == exception.getClass()) {
                throw exception;
            }
        }
        throw new NoFallbackAvailableException(exception.getMessage(), exception);
    }
}
