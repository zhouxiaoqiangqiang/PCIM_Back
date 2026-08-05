package com.example.pcim.mapper;

import com.example.pcim.entity.Prisoner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PrisonerMapper {
    Prisoner selectById(@Param("id") Long id);

    Prisoner selectByNo(@Param("prisonerNo") String prisonerNo);

    List<Prisoner> selectList(@Param("keyword") String keyword, @Param("cell") String cell, @Param("status") String status);

    Long selectCount(@Param("keyword") String keyword, @Param("cell") String cell, @Param("status") String status);

    int insert(Prisoner prisoner);

    int update(Prisoner prisoner);

    int deleteById(@Param("id") Long id);
}
