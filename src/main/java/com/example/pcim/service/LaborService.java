package com.example.pcim.service;

import com.example.pcim.dto.LaborDTO;
import com.example.pcim.dto.LaborQueryDTO;
import com.example.pcim.dto.PageResult;
import com.example.pcim.entity.Labor;

public interface LaborService {
    PageResult<Labor> getLaborList(LaborQueryDTO queryDTO);

    Labor getLaborById(Long id);

    int addLabor(LaborDTO laborDTO);

    int updateLabor(LaborDTO laborDTO);

    int deleteLabor(Long id);
}
