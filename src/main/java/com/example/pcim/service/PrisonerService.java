package com.example.pcim.service;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.PrisonerDTO;
import com.example.pcim.dto.PrisonerQueryDTO;
import com.example.pcim.entity.Prisoner;

public interface PrisonerService {
    PageResult<Prisoner> getPrisonerList(PrisonerQueryDTO queryDTO);

    Prisoner getPrisonerById(Long id);

    int addPrisoner(PrisonerDTO prisonerDTO);

    int updatePrisoner(PrisonerDTO prisonerDTO);

    int deletePrisoner(Long id);

    /**
     * 修改监犯风险等级
     * @param id 监犯ID
     * @param riskLevel 风险等级
     * @param reason 变更原因
     */
    void updateRiskLevel(Long id, String riskLevel, String reason);

}
