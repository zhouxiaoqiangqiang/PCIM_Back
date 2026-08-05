package com.example.pcim.mapper;

import com.example.pcim.entity.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    Attendance selectById(@Param("id") Long id);

    List<Attendance> selectList(@Param("keyword") String keyword, @Param("cell") String cell, @Param("status") String status);

    Long selectCount(@Param("keyword") String keyword, @Param("cell") String cell, @Param("status") String status);

    int insert(Attendance attendance);

    int update(Attendance attendance);

    int deleteById(@Param("id") Long id);
}
