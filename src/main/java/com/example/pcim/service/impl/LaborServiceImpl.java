package com.example.pcim.service.impl;

import com.example.pcim.dto.LaborDTO;
import com.example.pcim.dto.LaborQueryDTO;
import com.example.pcim.dto.PageResult;
import com.example.pcim.entity.Labor;
import com.example.pcim.exception.BusinessException;
import com.example.pcim.mapper.LaborMapper;
import com.example.pcim.service.LaborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaborServiceImpl implements LaborService {

    @Autowired
    private LaborMapper laborMapper;

    @Override
    public PageResult<Labor> getLaborList(LaborQueryDTO queryDTO) {
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        List<Labor> list = laborMapper.selectList(queryDTO.getKeyword(), queryDTO.getWorkType(), queryDTO.getStatus());
        Long total = laborMapper.selectCount(queryDTO.getKeyword(), queryDTO.getWorkType(), queryDTO.getStatus());

        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<Labor> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    @Override
    public Labor getLaborById(Long id) {
        return laborMapper.selectById(id);
    }

    @Override
    public int addLabor(LaborDTO laborDTO) {
        Labor labor = new Labor();
        labor.setPrisonerId(laborDTO.getPrisonerId());
        labor.setPrisonerNo(laborDTO.getPrisonerNo());
        labor.setName(laborDTO.getName());
        labor.setCell(laborDTO.getCell());
        labor.setWorkType(laborDTO.getWorkType());
        labor.setWorkPlace(laborDTO.getWorkPlace());
        labor.setWorkHours(laborDTO.getWorkHours());
        labor.setWorkScore(laborDTO.getWorkScore() != null ? laborDTO.getWorkScore() : 3);
        labor.setStatus(laborDTO.getStatus() != null ? laborDTO.getStatus() : "在岗");
        labor.setAssignDate(laborDTO.getAssignDate());
        labor.setRemark(laborDTO.getRemark());

        return laborMapper.insert(labor);
    }

    @Override
    public int updateLabor(LaborDTO laborDTO) {
        Labor labor = laborMapper.selectById(laborDTO.getId());
        if (labor == null) {
            throw new BusinessException("劳动分配不存在");
        }

        labor.setWorkType(laborDTO.getWorkType());
        labor.setWorkPlace(laborDTO.getWorkPlace());
        labor.setWorkHours(laborDTO.getWorkHours());
        labor.setWorkScore(laborDTO.getWorkScore());
        labor.setStatus(laborDTO.getStatus());
        labor.setAssignDate(laborDTO.getAssignDate());
        labor.setRemark(laborDTO.getRemark());

        return laborMapper.update(labor);
    }

    @Override
    public int deleteLabor(Long id) {
        Labor labor = laborMapper.selectById(id);
        if (labor == null) {
            throw new BusinessException("劳动分配不存在");
        }
        return laborMapper.deleteById(id);
    }
}
