package com.example.pcim.mapper;

import com.example.pcim.entity.RiskLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 风险变更日志 Mapper 接口
 */
@Mapper
public interface RiskLogMapper {

    /** 根据ID查询风险变更日志 */
    RiskLog selectById(@Param("id") Long id);

    /** 根据条件查询风险变更日志列表 */
    List<RiskLog> selectList(@Param("keyword") String keyword,
                             @Param("oldLevel") String oldLevel,
                             @Param("newLevel") String newLevel);

    /** 根据条件统计风险变更日志总数 */
    Long selectCount(@Param("keyword") String keyword,
                     @Param("oldLevel") String oldLevel,
                     @Param("newLevel") String newLevel);

    /** 新增风险变更日志 */
    int insert(RiskLog riskLog);

    /** 更新风险变更日志 */
    int update(RiskLog riskLog);

    /** 根据ID删除风险变更日志 */
    int deleteById(@Param("id") Long id);
}
