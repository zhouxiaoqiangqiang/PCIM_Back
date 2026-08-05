package com.example.pcim.service;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.UserDTO;
import com.example.pcim.dto.UserQueryDTO;
import com.example.pcim.entity.User;

public interface UserService {
    User login(String username, String password);

    PageResult<User> getUserList(UserQueryDTO queryDTO);

    User getUserById(Long id);

    int addUser(UserDTO userDTO);

    int updateUser(UserDTO userDTO);

    int deleteUser(Long id);

    int resetPassword(Long id);

    int changeStatus(Long id, Integer status);
}
