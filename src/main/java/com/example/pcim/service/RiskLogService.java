package com.example.pcim.service;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.RiskLogDTO;
import com.example.pcim.dto.RiskLogQueryDTO;
import com.example.pcim.entity.RiskLog;

/**
 * 风险变更日志 Service 接口
 */
public interface RiskLogService {

    /** 分页查询风险变更日志列表 */
    PageResult<RiskLog> getRiskLogList(RiskLogQueryDTO queryDTO);

    /** 根据ID查询风险变更日志 */
    RiskLog getRiskLogById(Long id);

    /** 新增风险变更日志 */
    int addRiskLog(RiskLogDTO riskLogDTO);

    /** 更新风险变更日志 */
    int updateRiskLog(RiskLogDTO riskLogDTO);

    /** 删除风险变更日志 */
    int deleteRiskLog(Long id);
}
