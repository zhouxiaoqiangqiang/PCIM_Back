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
}
