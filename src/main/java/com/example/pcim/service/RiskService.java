package com.example.pcim.service;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.RiskDTO;
import com.example.pcim.dto.RiskQueryDTO;
import com.example.pcim.entity.Risk;

/**
 * 风险评估 Service 接口
 */
public interface RiskService {

    /** 分页查询风险评估列表 */
    PageResult<Risk> getRiskList(RiskQueryDTO queryDTO);

    /** 根据ID查询风险评估 */
    Risk getRiskById(Long id);

    /** 新增风险评估（自动计算总分和风险等级） */
    int addRisk(RiskDTO riskDTO);

    /** 更新风险评估（自动计算总分和风险等级，等级变化时记录日志） */
    int updateRisk(RiskDTO riskDTO);

    /** 删除风险评估 */
    int deleteRisk(Long id);
}
