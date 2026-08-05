package com.example.pcim.service.impl;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.UserDTO;
import com.example.pcim.dto.UserQueryDTO;
import com.example.pcim.entity.User;
import com.example.pcim.exception.BusinessException;
import com.example.pcim.mapper.UserMapper;
import com.example.pcim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!encryptedPassword.equals(user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用");
        }
        return user;
    }

    @Override
    public PageResult<User> getUserList(UserQueryDTO queryDTO) {
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        List<User> list = userMapper.selectList(queryDTO.getKeyword(), queryDTO.getStatus());
        Long total = userMapper.selectCount(queryDTO.getKeyword(), queryDTO.getStatus());

        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<User> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public int addUser(UserDTO userDTO) {
        User existingUser = userMapper.selectByUsername(userDTO.getUsername());
        if (existingUser != null) {
            throw new BusinessException("用户名已存在");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()));
        user.setRealName(userDTO.getRealName());
        user.setRole(userDTO.getRole());
        user.setPhone(userDTO.getPhone());
        user.setStatus(userDTO.getStatus() != null ? userDTO.getStatus() : 1);

        return userMapper.insert(user);
    }

    @Override
    public int updateUser(UserDTO userDTO) {
        User user = userMapper.selectById(userDTO.getId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        User existingUser = userMapper.selectByUsername(userDTO.getUsername());
        if (existingUser != null && !existingUser.getId().equals(userDTO.getId())) {
            throw new BusinessException("用户名已存在");
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()));
        }
        if (userDTO.getRealName() != null) {
            user.setRealName(userDTO.getRealName());
        }
        if (userDTO.getRole() != null) {
            user.setRole(userDTO.getRole());
        }
        if (userDTO.getPhone() != null) {
            user.setPhone(userDTO.getPhone());
        }
        if (userDTO.getStatus() != null) {
            user.setStatus(userDTO.getStatus());
        }

        return userMapper.update(user);
    }

    @Override
    public int deleteUser(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return userMapper.deleteById(id);
    }

    @Override
    public int resetPassword(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        return userMapper.update(user);
    }

    @Override
    public int changeStatus(Long id, Integer status) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setStatus(status);
        return userMapper.update(user);
    }
}
