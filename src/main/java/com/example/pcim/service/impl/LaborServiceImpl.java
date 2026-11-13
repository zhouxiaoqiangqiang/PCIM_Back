package com.example.pcim.service.impl;

import com.example.pcim.dto.LaborDTO;
import com.example.pcim.dto.LaborQueryDTO;
import com.example.pcim.dto.PageResult;
import com.example.pcim.entity.Labor;
import com.example.pcim.entity.Prisoner;
import com.example.pcim.exception.BusinessException;
import com.example.pcim.mapper.LaborMapper;
import com.example.pcim.mapper.PrisonerMapper;
import com.example.pcim.service.LaborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaborServiceImpl implements LaborService {

    @Autowired
    private LaborMapper laborMapper;

    /** 监犯 Mapper：分配劳动时按编号回填姓名、监区 */
    @Autowired
    private PrisonerMapper prisonerMapper;

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
        // 按监犯编号关联监犯档案，校验编号是否存在
        if (laborDTO.getPrisonerNo() == null || laborDTO.getPrisonerNo().trim().isEmpty()) {
            throw new BusinessException("监犯编号不能为空");
        }
        Prisoner prisoner = prisonerMapper.selectByNo(laborDTO.getPrisonerNo().trim());
        if (prisoner == null) {
            throw new BusinessException("监犯编号不存在：" + laborDTO.getPrisonerNo());
        }

        Labor labor = new Labor();
        // 编号、姓名、监区统一取监犯档案，避免前端只传编号导致姓名监区为空
        labor.setPrisonerId(prisoner.getId());
        labor.setPrisonerNo(prisoner.getPrisonerNo());
        labor.setName(prisoner.getName());
        labor.setCell(prisoner.getCell());
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
