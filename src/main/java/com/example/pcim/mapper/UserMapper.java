package com.example.pcim.mapper;

import com.example.pcim.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User selectByUsername(@Param("username") String username);

    User selectById(@Param("id") Long id);

    List<User> selectList(@Param("keyword") String keyword, @Param("status") Integer status);

    Long selectCount(@Param("keyword") String keyword, @Param("status") Integer status);

    int insert(User user);

    int update(User user);

    int deleteById(@Param("id") Long id);
}
