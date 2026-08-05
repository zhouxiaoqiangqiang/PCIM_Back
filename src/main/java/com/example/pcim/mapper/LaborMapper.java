package com.example.pcim.mapper;

import com.example.pcim.entity.Labor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LaborMapper {
    Labor selectById(@Param("id") Long id);

    List<Labor> selectList(@Param("keyword") String keyword, @Param("workType") String workType, @Param("status") String status);

    Long selectCount(@Param("keyword") String keyword, @Param("workType") String workType, @Param("status") String status);

    int insert(Labor labor);

    int update(Labor labor);

    int deleteById(@Param("id") Long id);
}
