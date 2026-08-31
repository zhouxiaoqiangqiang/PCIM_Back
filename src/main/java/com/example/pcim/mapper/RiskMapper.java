package com.example.pcim.mapper;

import com.example.pcim.entity.Risk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 风险评估 Mapper 接口
 */
@Mapper
public interface RiskMapper {

    /** 根据ID查询风险评估记录 */
    Risk selectById(@Param("id") Long id);

    /** 根据条件查询风险评估列表 */
    List<Risk> selectList(@Param("keyword") String keyword,
                          @Param("cell") String cell,
                          @Param("riskLevel") String riskLevel);

    /** 根据条件统计风险评估总数 */
    Long selectCount(@Param("keyword") String keyword,
                     @Param("cell") String cell,
                     @Param("riskLevel") String riskLevel);

    /** 新增风险评估记录 */
    int insert(Risk risk);

    /** 更新风险评估记录 */
    int update(Risk risk);

    /** 根据ID删除风险评估记录 */
    int deleteById(@Param("id") Long id);
}
