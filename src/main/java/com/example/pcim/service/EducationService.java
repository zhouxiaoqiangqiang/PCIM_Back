package com.example.pcim.service;

import com.example.pcim.dto.EducationDTO;
import com.example.pcim.dto.EducationQueryDTO;
import com.example.pcim.dto.PageResult;
import com.example.pcim.entity.Education;

/**
 * 教育学习 Service 接口
 * 定义教育学习模块的业务逻辑方法
 */
public interface EducationService {

    /**
     * 分页查询教育学习列表
     *
     * @param queryDTO 查询条件（含分页参数）
     * @return 分页结果（列表 + 总条数）
     */
    PageResult<Education> getEducationList(EducationQueryDTO queryDTO);

    /**
     * 根据ID查询单条教育学习记录
     *
     * @param id 主键ID
     * @return 教育学习实体
     */
    Education getEducationById(Long id);

    /**
     * 新增一条教育学习记录
     *
     * @param educationDTO 前端传入的教育学习数据
     * @return 受影响的行数
     */
    int addEducation(EducationDTO educationDTO);

    /**
     * 更新一条教育学习记录
     *
     * @param educationDTO 前端传入的教育学习数据（必须包含id）
     * @return 受影响的行数
     */
    int updateEducation(EducationDTO educationDTO);

    /**
     * 根据ID删除一条教育学习记录
     *
     * @param id 主键ID
     * @return 受影响的行数
     */
    int deleteEducation(Long id);
}
