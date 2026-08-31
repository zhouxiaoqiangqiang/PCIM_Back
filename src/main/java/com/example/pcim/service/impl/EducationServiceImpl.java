package com.example.pcim.service.impl;

import com.example.pcim.dto.EducationDTO;
import com.example.pcim.dto.EducationQueryDTO;
import com.example.pcim.dto.PageResult;
import com.example.pcim.entity.Education;
import com.example.pcim.exception.BusinessException;
import com.example.pcim.mapper.EducationMapper;
import com.example.pcim.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教育学习 Service 实现类
 * 实现教育学习模块的业务逻辑，包括分页查询、CRUD 等
 */
@Service
public class EducationServiceImpl implements EducationService {

    /** 注入教育学习 Mapper */
    @Autowired
    private EducationMapper educationMapper;

    @Override
    public PageResult<Education> getEducationList(EducationQueryDTO queryDTO) {
        // 处理分页参数，默认第1页、每页10条
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        // 查询全部符合条件的记录（由 Mapper 排序好）
        List<Education> list = educationMapper.selectList(
                queryDTO.getKeyword(), queryDTO.getResult());
        // 查询总记录数
        Long total = educationMapper.selectCount(
                queryDTO.getKeyword(), queryDTO.getResult());

        // 内存分页（与 PrisonerServiceImpl 保持一致）
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<Education> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    @Override
    public Education getEducationById(Long id) {
        return educationMapper.selectById(id);
    }

    @Override
    public int addEducation(EducationDTO educationDTO) {
        // 将 DTO 转换为 Entity
        Education education = convertDtoToEntity(educationDTO);
        return educationMapper.insert(education);
    }

    @Override
    public int updateEducation(EducationDTO educationDTO) {
        // 更新前先校验记录是否存在
        Education existing = educationMapper.selectById(educationDTO.getId());
        if (existing == null) {
            throw new BusinessException("教育学习记录不存在");
        }

        Education education = convertDtoToEntity(educationDTO);
        return educationMapper.update(education);
    }

    @Override
    public int deleteEducation(Long id) {
        // 删除前先校验记录是否存在
        Education existing = educationMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException("教育学习记录不存在");
        }
        return educationMapper.deleteById(id);
    }

    /**
     * DTO 转 Entity 的私有工具方法
     *
     * @param dto 数据传输对象
     * @return 教育学习实体
     */
    private Education convertDtoToEntity(EducationDTO dto) {
        Education education = new Education();
        education.setId(dto.getId());
        education.setPrisonerNo(dto.getPrisonerNo());
        education.setName(dto.getName());
        education.setCell(dto.getCell());
        education.setCourseName(dto.getCourseName());
        education.setEduDate(dto.getEduDate());
        education.setDuration(dto.getDuration());
        education.setTeacher(dto.getTeacher());
        education.setScore(dto.getScore());
        education.setResult(dto.getResult());
        education.setRemark(dto.getRemark());
        return education;
    }
}
