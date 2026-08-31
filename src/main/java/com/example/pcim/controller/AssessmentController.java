package com.example.pcim.controller;

import com.example.pcim.dto.AssessmentDTO;
import com.example.pcim.dto.AssessmentQueryDTO;
import com.example.pcim.dto.Response;
import com.example.pcim.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 考核评估 Controller
 * 处理考核评估模块的 HTTP 请求，路径前缀 /api/assessments
 */
@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    /** 注入考核评估 Service */
    @Autowired
    private AssessmentService assessmentService;

    /**
     * 分页查询考核评估列表
     * 请求方式：POST
     * 请求体：AssessmentQueryDTO JSON
     *
     * @param queryDTO 分页查询条件
     * @return 分页结果（列表 + 总条数）
     */
    @PostMapping("/list")
    public Response<?> getAssessmentList(@RequestBody AssessmentQueryDTO queryDTO) {
        return Response.success(assessmentService.getAssessmentList(queryDTO));
    }

    /**
     * 根据ID查询单条考核评估记录
     * 请求方式：GET
     * 路径参数：id - 考核记录主键ID
     *
     * @param id 主键ID
     * @return 考核评估实体
     */
    @GetMapping("/{id}")
    public Response<?> getAssessmentById(@PathVariable Long id) {
        return Response.success(assessmentService.getAssessmentById(id));
    }

    /**
     * 新增一条考核评估记录
     * 请求方式：POST
     * 请求体：AssessmentDTO JSON
     *
     * @param assessmentDTO 考核评估数据
     * @return 操作提示
     */
    @PostMapping
    public Response<?> addAssessment(@RequestBody AssessmentDTO assessmentDTO) {
        assessmentService.addAssessment(assessmentDTO);
        return Response.success("新增成功");
    }

    /**
     * 更新一条考核评估记录
     * 请求方式：PUT
     * 请求体：AssessmentDTO JSON（必须包含 id 字段）
     *
     * @param assessmentDTO 考核评估数据
     * @return 操作提示
     */
    @PutMapping
    public Response<?> updateAssessment(@RequestBody AssessmentDTO assessmentDTO) {
        assessmentService.updateAssessment(assessmentDTO);
        return Response.success("修改成功");
    }

    /**
     * 根据ID删除一条考核评估记录
     * 请求方式：DELETE
     * 路径参数：id - 考核记录主键ID
     *
     * @param id 主键ID
     * @return 操作提示
     */
    @DeleteMapping("/{id}")
    public Response<?> deleteAssessment(@PathVariable Long id) {
        assessmentService.deleteAssessment(id);
        return Response.success("删除成功");
    }

    /**
     * 执行月度汇总
     * 请求方式：POST
     * 路径：/api/assessments/monthly-summary
     * 业务逻辑：
     *   1. 计算上一年上月
     *   2. 清理该月旧的汇总记录
     *   3. 查询上月所有非汇总考核记录，按监犯编号分组计算总分
     *   4. 生成 type='月度汇总' 的新记录并批量插入
     *
     * @return 操作结果提示（包含汇总月份和生成条数）
     */
    @PostMapping("/monthly-summary")
    public Response<?> runMonthlySummary() {
        String result = assessmentService.runMonthlySummary();
        return Response.success(result);
    }
}
