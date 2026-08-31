package com.example.pcim.service.impl;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.PrisonerDTO;
import com.example.pcim.dto.PrisonerQueryDTO;
import com.example.pcim.entity.Prisoner;
import com.example.pcim.entity.RiskLog;
import com.example.pcim.exception.BusinessException;
import com.example.pcim.mapper.PrisonerMapper;
import com.example.pcim.mapper.RiskLogMapper;
import com.example.pcim.service.PrisonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrisonerServiceImpl implements PrisonerService {

    @Autowired
    private PrisonerMapper prisonerMapper;

    /** 风险变更日志 Mapper，修改风险等级时自动写入日志 */
    @Autowired
    private RiskLogMapper riskLogMapper;

    @Override
    public PageResult<Prisoner> getPrisonerList(PrisonerQueryDTO queryDTO) {
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        List<Prisoner> list = prisonerMapper.selectList(queryDTO.getKeyword(), queryDTO.getCell(), queryDTO.getStatus());
        Long total = prisonerMapper.selectCount(queryDTO.getKeyword(), queryDTO.getCell(), queryDTO.getStatus());

        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<Prisoner> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    @Override
    public Prisoner getPrisonerById(Long id) {
        return prisonerMapper.selectById(id);
    }

    @Override
    public int addPrisoner(PrisonerDTO prisonerDTO) {
        Prisoner existing = prisonerMapper.selectByNo(prisonerDTO.getPrisonerNo());
        if (existing != null) {
            throw new BusinessException("监犯编号已存在");
        }

        Prisoner prisoner = new Prisoner();
        prisoner.setPrisonerNo(prisonerDTO.getPrisonerNo());
        prisoner.setName(prisonerDTO.getName());
        prisoner.setGender(prisonerDTO.getGender());
        prisoner.setAge(prisonerDTO.getAge());
        prisoner.setIdCard(prisonerDTO.getIdCard());
        prisoner.setCell(prisonerDTO.getCell());
        prisoner.setCrimeType(prisonerDTO.getCrimeType());
        prisoner.setSentence(prisonerDTO.getSentence());
        prisoner.setEntryDate(prisonerDTO.getEntryDate());
        prisoner.setBirthDate(prisonerDTO.getBirthDate());
        prisoner.setHometown(prisonerDTO.getHometown());
        prisoner.setSocialBackground(prisonerDTO.getSocialBackground());
        prisoner.setOccupation(prisonerDTO.getOccupation());
        prisoner.setPhoto(prisonerDTO.getPhoto());
        prisoner.setStatus(prisonerDTO.getStatus() != null ? prisonerDTO.getStatus() : "在押");
        prisoner.setRemark(prisonerDTO.getRemark());

        return prisonerMapper.insert(prisoner);
    }

    @Override
    public int updatePrisoner(PrisonerDTO prisonerDTO) {
        Prisoner prisoner = prisonerMapper.selectById(prisonerDTO.getId());
        if (prisoner == null) {
            throw new BusinessException("监犯不存在");
        }

        Prisoner existing = prisonerMapper.selectByNo(prisonerDTO.getPrisonerNo());
        if (existing != null && !existing.getId().equals(prisonerDTO.getId())) {
            throw new BusinessException("监犯编号已存在");
        }

        prisoner.setName(prisonerDTO.getName());
        prisoner.setGender(prisonerDTO.getGender());
        prisoner.setAge(prisonerDTO.getAge());
        prisoner.setIdCard(prisonerDTO.getIdCard());
        prisoner.setCell(prisonerDTO.getCell());
        prisoner.setCrimeType(prisonerDTO.getCrimeType());
        prisoner.setSentence(prisonerDTO.getSentence());
        prisoner.setEntryDate(prisonerDTO.getEntryDate());
        prisoner.setBirthDate(prisonerDTO.getBirthDate());
        prisoner.setHometown(prisonerDTO.getHometown());
        prisoner.setSocialBackground(prisonerDTO.getSocialBackground());
        prisoner.setOccupation(prisonerDTO.getOccupation());
        prisoner.setPhoto(prisonerDTO.getPhoto());
        prisoner.setStatus(prisonerDTO.getStatus());
        prisoner.setRemark(prisonerDTO.getRemark());

        return prisonerMapper.update(prisoner);
    }

    @Override
    public int deletePrisoner(Long id) {
        Prisoner prisoner = prisonerMapper.selectById(id);
        if (prisoner == null) {
            throw new BusinessException("监犯不存在");
        }
        return prisonerMapper.deleteById(id);
    }


    /**
     * 修改监犯风险等级
     * 1. 校验监犯是否存在
     * 2. 若新旧等级相同则提示无需变更
     * 3. 更新 risk_level 字段
     * 4. 自动写入风险变更日志（prison_risk_log）
     */
    @Override
    public void updateRiskLevel(Long id, String riskLevel, String reason) {
        Prisoner prisoner = prisonerMapper.selectById(id);
        if (prisoner == null) {
            throw new BusinessException("监犯不存在");
        }

        String oldLevel = prisoner.getRiskLevel();
        if (riskLevel.equals(oldLevel)) {
            throw new BusinessException("当前已是「" + riskLevel + "」，请选择不同的等级");
        }

        // 更新风险等级
        prisonerMapper.updateRiskLevel(id, riskLevel);

        // 写入风险变更日志
        RiskLog log = new RiskLog();
        log.setPrisonerNo(prisoner.getPrisonerNo());
        log.setName(prisoner.getName());
        log.setOldLevel(oldLevel != null ? oldLevel : "未评定");
        log.setNewLevel(riskLevel);
        log.setOperator("系统"); // 实际可从 SecurityContext 获取当前登录用户
        log.setRemark(reason != null ? reason : "手动调整风险等级");

        riskLogMapper.insert(log);
    }
}
