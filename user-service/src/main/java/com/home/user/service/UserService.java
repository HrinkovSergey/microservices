package com.home.user.service;

import com.home.user.domain.User;
import com.home.user.dto.UserDto;

public interface UserService {
    UserDto saveUser(User user);

    UserDto findUserById(String userId);
}
