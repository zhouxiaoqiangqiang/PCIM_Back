package com.example.pcim.controller;

import com.example.pcim.dto.Response;
import com.example.pcim.dto.VisitDTO;
import com.example.pcim.dto.VisitQueryDTO;
import com.example.pcim.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 探视登记 Controller
 * 处理探视登记模块的 HTTP 请求，路径前缀 /api/visits
 */
@RestController
@RequestMapping("/api/visits")
public class VisitController {

    /** 注入探视登记 Service */
    @Autowired
    private VisitService visitService;

    /**
     * 分页查询探视登记列表
     * 请求方式：POST
     * 请求体：VisitQueryDTO JSON
     *
     * @param queryDTO 分页查询条件
     * @return 分页结果（列表 + 总条数）
     */
    @PostMapping("/list")
    public Response<?> getList(@RequestBody VisitQueryDTO queryDTO) {
        return Response.success(visitService.getList(queryDTO));
    }

    /**
     * 根据ID查询单条探视登记
     * 请求方式：GET
     * 路径参数：id - 探视登记主键ID
     *
     * @param id 主键ID
     * @return 探视登记实体
     */
    @GetMapping("/{id}")
    public Response<?> getById(@PathVariable Long id) {
        return Response.success(visitService.getById(id));
    }

    /**
     * 新增一条探视登记
     * 请求方式：POST
     * 请求体：VisitDTO JSON
     *
     * @param visitDTO 探视登记数据
     * @return 操作提示
     */
    @PostMapping
    public Response<?> add(@RequestBody VisitDTO visitDTO) {
        visitService.add(visitDTO);
        return Response.success("新增成功");
    }

    /**
     * 更新一条探视登记
     * 请求方式：PUT
     * 请求体：VisitDTO JSON（必须包含 id 字段）
     *
     * @param visitDTO 探视登记数据
     * @return 操作提示
     */
    @PutMapping
    public Response<?> update(@RequestBody VisitDTO visitDTO) {
        visitService.update(visitDTO);
        return Response.success("修改成功");
    }

    /**
     * 根据ID删除一条探视登记
     * 请求方式：DELETE
     * 路径参数：id - 探视登记主键ID
     *
     * @param id 主键ID
     * @return 操作提示
     */
    @DeleteMapping("/{id}")
    public Response<?> delete(@PathVariable Long id) {
        visitService.delete(id);
        return Response.success("删除成功");
    }
}
