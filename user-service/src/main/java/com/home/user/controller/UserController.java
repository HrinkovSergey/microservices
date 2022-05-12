package com.home.user.controller;

import com.home.user.domain.User;
import com.home.user.dto.UserDto;
import com.home.user.dto.UserForCreatingDto;
import com.home.user.mapper.UserMapper;
import com.home.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public UserDto saveUser(@RequestBody UserForCreatingDto userForCreatingDto) {
        User user = UserMapper.INSTANCE.toEntity(userForCreatingDto);
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable("id") String userId) {
        return userService.findUserById(userId);
    }


}
