package com.example.pcim.service.impl;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.RiskLogDTO;
import com.example.pcim.dto.RiskLogQueryDTO;
import com.example.pcim.entity.RiskLog;
import com.example.pcim.exception.BusinessException;
import com.example.pcim.mapper.RiskLogMapper;
import com.example.pcim.service.RiskLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 风险变更日志 Service 实现类
 */
@Service
public class RiskLogServiceImpl implements RiskLogService {

    @Autowired
    private RiskLogMapper riskLogMapper;

    /**
     * 分页查询风险变更日志列表
     */
    @Override
    public PageResult<RiskLog> getRiskLogList(RiskLogQueryDTO queryDTO) {
        // 默认分页参数
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        // 查询全量列表与总数
        List<RiskLog> list = riskLogMapper.selectList(queryDTO.getKeyword(), queryDTO.getOldLevel(), queryDTO.getNewLevel());
        Long total = riskLogMapper.selectCount(queryDTO.getKeyword(), queryDTO.getOldLevel(), queryDTO.getNewLevel());

        // 内存分页
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<RiskLog> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    /**
     * 根据ID查询风险变更日志
     */
    @Override
    public RiskLog getRiskLogById(Long id) {
        RiskLog riskLog = riskLogMapper.selectById(id);
        if (riskLog == null) {
            throw new BusinessException("风险变更日志不存在");
        }
        return riskLog;
    }

    /**
     * 新增风险变更日志
     */
    @Override
    public int addRiskLog(RiskLogDTO riskLogDTO) {
        RiskLog riskLog = new RiskLog();
        riskLog.setPrisonerNo(riskLogDTO.getPrisonerNo());
        riskLog.setName(riskLogDTO.getName());
        riskLog.setOldLevel(riskLogDTO.getOldLevel());
        riskLog.setNewLevel(riskLogDTO.getNewLevel());
        riskLog.setOperator(riskLogDTO.getOperator());
        riskLog.setRemark(riskLogDTO.getRemark());

        return riskLogMapper.insert(riskLog);
    }

    /**
     * 更新风险变更日志
     */
    @Override
    public int updateRiskLog(RiskLogDTO riskLogDTO) {
        RiskLog riskLog = riskLogMapper.selectById(riskLogDTO.getId());
        if (riskLog == null) {
            throw new BusinessException("风险变更日志不存在");
        }

        riskLog.setPrisonerNo(riskLogDTO.getPrisonerNo());
        riskLog.setName(riskLogDTO.getName());
        riskLog.setOldLevel(riskLogDTO.getOldLevel());
        riskLog.setNewLevel(riskLogDTO.getNewLevel());
        riskLog.setOperator(riskLogDTO.getOperator());
        riskLog.setRemark(riskLogDTO.getRemark());

        return riskLogMapper.update(riskLog);
    }

    /**
     * 删除风险变更日志
     */
    @Override
    public int deleteRiskLog(Long id) {
        RiskLog riskLog = riskLogMapper.selectById(id);
        if (riskLog == null) {
            throw new BusinessException("风险变更日志不存在");
        }
        return riskLogMapper.deleteById(id);
    }
}
