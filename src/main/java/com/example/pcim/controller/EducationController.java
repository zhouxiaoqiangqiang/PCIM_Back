package com.example.pcim.controller;

import com.example.pcim.dto.EducationDTO;
import com.example.pcim.dto.EducationQueryDTO;
import com.example.pcim.dto.Response;
import com.example.pcim.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 教育学习 Controller
 * 处理教育学习模块的 HTTP 请求，路径前缀 /api/educations
 */
@RestController
@RequestMapping("/api/educations")
public class EducationController {

    /** 注入教育学习 Service */
    @Autowired
    private EducationService educationService;

    /**
     * 分页查询教育学习列表
     * 请求方式：POST
     * 请求体：EducationQueryDTO JSON
     *
     * @param queryDTO 分页查询条件
     * @return 分页结果（列表 + 总条数）
     */
    @PostMapping("/list")
    public Response<?> getEducationList(@RequestBody EducationQueryDTO queryDTO) {
        return Response.success(educationService.getEducationList(queryDTO));
    }

    /**
     * 根据ID查询单条教育学习记录
     * 请求方式：GET
     * 路径参数：id - 教育记录主键ID
     *
     * @param id 主键ID
     * @return 教育学习实体
     */
    @GetMapping("/{id}")
    public Response<?> getEducationById(@PathVariable Long id) {
        return Response.success(educationService.getEducationById(id));
    }

    /**
     * 新增一条教育学习记录
     * 请求方式：POST
     * 请求体：EducationDTO JSON
     *
     * @param educationDTO 教育学习数据
     * @return 操作提示
     */
    @PostMapping
    public Response<?> addEducation(@RequestBody EducationDTO educationDTO) {
        educationService.addEducation(educationDTO);
        return Response.success("新增成功");
    }

    /**
     * 更新一条教育学习记录
     * 请求方式：PUT
     * 请求体：EducationDTO JSON（必须包含 id 字段）
     *
     * @param educationDTO 教育学习数据
     * @return 操作提示
     */
    @PutMapping
    public Response<?> updateEducation(@RequestBody EducationDTO educationDTO) {
        educationService.updateEducation(educationDTO);
        return Response.success("修改成功");
    }

    /**
     * 根据ID删除一条教育学习记录
     * 请求方式：DELETE
     * 路径参数：id - 教育记录主键ID
     *
     * @param id 主键ID
     * @return 操作提示
     */
    @DeleteMapping("/{id}")
    public Response<?> deleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);
        return Response.success("删除成功");
    }
}
