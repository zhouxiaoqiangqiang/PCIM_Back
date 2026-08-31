package com.example.pcim.service.impl;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.RiskDTO;
import com.example.pcim.dto.RiskQueryDTO;
import com.example.pcim.entity.Risk;
import com.example.pcim.entity.RiskLog;
import com.example.pcim.exception.BusinessException;
import com.example.pcim.mapper.RiskLogMapper;
import com.example.pcim.mapper.RiskMapper;
import com.example.pcim.service.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 风险评估 Service 实现类
 */
@Service
public class RiskServiceImpl implements RiskService {

    @Autowired
    private RiskMapper riskMapper;

    @Autowired
    private RiskLogMapper riskLogMapper;

    /**
     * 分页查询风险评估列表
     */
    @Override
    public PageResult<Risk> getRiskList(RiskQueryDTO queryDTO) {
        // 默认分页参数
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        // 查询全量列表与总数
        List<Risk> list = riskMapper.selectList(queryDTO.getKeyword(), queryDTO.getCell(), queryDTO.getRiskLevel());
        Long total = riskMapper.selectCount(queryDTO.getKeyword(), queryDTO.getCell(), queryDTO.getRiskLevel());

        // 内存分页
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<Risk> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    /**
     * 根据ID查询风险评估
     */
    @Override
    public Risk getRiskById(Long id) {
        Risk risk = riskMapper.selectById(id);
        if (risk == null) {
            throw new BusinessException("风险评估记录不存在");
        }
        return risk;
    }

    /**
     * 新增风险评估
     * 自动计算 totalScore 和 riskLevel
     */
    @Override
    public int addRisk(RiskDTO riskDTO) {
        // 转换为实体
        Risk risk = new Risk();
        risk.setPrisonerNo(riskDTO.getPrisonerNo());
        risk.setName(riskDTO.getName());
        risk.setCell(riskDTO.getCell());
        risk.setCrimeType(riskDTO.getCrimeType());
        risk.setAssessDate(riskDTO.getAssessDate());
        risk.setViolence(riskDTO.getViolence());
        risk.setEscape(riskDTO.getEscape());
        risk.setSuicide(riskDTO.getSuicide());
        risk.setHealth(riskDTO.getHealth());
        risk.setAssessor(riskDTO.getAssessor());
        risk.setRemark(riskDTO.getRemark());

        // 自动计算总分
        int totalScore = calcTotalScore(risk);
        risk.setTotalScore(totalScore);
        // 自动计算风险等级
        String riskLevel = calcRiskLevel(totalScore);
        risk.setRiskLevel(riskLevel);

        return riskMapper.insert(risk);
    }

    /**
     * 更新风险评估
     * 自动计算 totalScore 和 riskLevel，若风险等级发生变化则自动写入 RiskLog
     */
    @Override
    public int updateRisk(RiskDTO riskDTO) {
        // 查询原始记录
        Risk existRisk = riskMapper.selectById(riskDTO.getId());
        if (existRisk == null) {
            throw new BusinessException("风险评估记录不存在");
        }

        // 暂存旧的风险等级用于比较
        String oldLevel = existRisk.getRiskLevel();

        // 使用 DTO 中的字段更新实体
        existRisk.setPrisonerNo(riskDTO.getPrisonerNo());
        existRisk.setName(riskDTO.getName());
        existRisk.setCell(riskDTO.getCell());
        existRisk.setCrimeType(riskDTO.getCrimeType());
        existRisk.setAssessDate(riskDTO.getAssessDate());
        existRisk.setViolence(riskDTO.getViolence());
        existRisk.setEscape(riskDTO.getEscape());
        existRisk.setSuicide(riskDTO.getSuicide());
        existRisk.setHealth(riskDTO.getHealth());
        existRisk.setAssessor(riskDTO.getAssessor());
        existRisk.setRemark(riskDTO.getRemark());

        // 自动重新计算总分
        int totalScore = calcTotalScore(existRisk);
        existRisk.setTotalScore(totalScore);
        // 自动重新计算风险等级
        String newLevel = calcRiskLevel(totalScore);
        existRisk.setRiskLevel(newLevel);

        int rows = riskMapper.update(existRisk);

        // 若风险等级发生变化，自动记录 RiskLog
        if (oldLevel != null && !oldLevel.equals(newLevel)) {
            RiskLog log = new RiskLog();
            log.setPrisonerNo(existRisk.getPrisonerNo());
            log.setName(existRisk.getName());
            log.setOldLevel(oldLevel);
            log.setNewLevel(newLevel);
            log.setOperator(existRisk.getAssessor());
            log.setRemark("风险等级由[" + oldLevel + "]变更为[" + newLevel + "]");
            riskLogMapper.insert(log);
        }

        return rows;
    }

    /**
     * 删除风险评估
     */
    @Override
    public int deleteRisk(Long id) {
        Risk risk = riskMapper.selectById(id);
        if (risk == null) {
            throw new BusinessException("风险评估记录不存在");
        }
        return riskMapper.deleteById(id);
    }

    /**
     * 计算风险总分：暴力 + 逃跑 + 自杀 + 健康
     */
    private int calcTotalScore(Risk risk) {
        return (risk.getViolence() != null ? risk.getViolence() : 0)
                + (risk.getEscape() != null ? risk.getEscape() : 0)
                + (risk.getSuicide() != null ? risk.getSuicide() : 0)
                + (risk.getHealth() != null ? risk.getHealth() : 0);
    }

    /**
     * 根据总分计算风险等级
     * >= 200 高风险
     * >= 100 中风险
     * < 100  低风险
     */
    private String calcRiskLevel(int totalScore) {
        if (totalScore >= 200) {
            return "高风险";
        } else if (totalScore >= 100) {
            return "中风险";
        } else {
            return "低风险";
        }
    }
}
