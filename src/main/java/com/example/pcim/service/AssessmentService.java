package com.example.pcim.service;

import com.example.pcim.dto.AssessmentDTO;
import com.example.pcim.dto.AssessmentQueryDTO;
import com.example.pcim.dto.PageResult;
import com.example.pcim.entity.Assessment;

/**
 * 考核评估 Service 接口
 * 定义考核评估模块的业务逻辑方法
 */
public interface AssessmentService {

    /**
     * 分页查询考核评估列表
     *
     * @param queryDTO 查询条件（含分页参数）
     * @return 分页结果（列表 + 总条数）
     */
    PageResult<Assessment> getAssessmentList(AssessmentQueryDTO queryDTO);

    /**
     * 根据ID查询单条考核评估记录
     *
     * @param id 主键ID
     * @return 考核评估实体
     */
    Assessment getAssessmentById(Long id);

    /**
     * 新增一条考核评估记录
     *
     * @param assessmentDTO 前端传入的考核评估数据
     * @return 受影响的行数
     */
    int addAssessment(AssessmentDTO assessmentDTO);

    /**
     * 更新一条考核评估记录
     *
     * @param assessmentDTO 前端传入的考核评估数据（必须包含id）
     * @return 受影响的行数
     */
    int updateAssessment(AssessmentDTO assessmentDTO);

    /**
     * 根据ID删除一条考核评估记录
     *
     * @param id 主键ID
     * @return 受影响的行数
     */
    int deleteAssessment(Long id);

    /**
     * 执行月度汇总
     * 统计上一个月所有非汇总的考核记录，按监犯编号分组后计算总分，
     * 并生成 type='月度汇总' 的新记录插入数据库。
     * 同月重复执行会先清理旧的汇总记录，保证幂等。
     *
     * @return 操作结果提示字符串（包含汇总月份和生成条数）
     */
    String runMonthlySummary();
}
