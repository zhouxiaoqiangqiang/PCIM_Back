package com.example.pcim.controller;

import com.example.pcim.dto.ReleaseDTO;
import com.example.pcim.dto.ReleaseQueryDTO;
import com.example.pcim.dto.Response;
import com.example.pcim.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 离监申请 Controller
 * 提供离监申请相关的 REST API 接口
 * <p>
 * API 路径前缀：/api/releases
 */
@RestController
@RequestMapping("/api/releases")
public class ReleaseController {

    /**
     * 注入离监申请 Service
     */
    @Autowired
    private ReleaseService releaseService;

    /**
     * 分页查询离监申请列表
     * 支持关键字、状态、类型等条件筛选
     *
     * @param queryDTO 查询条件（含分页参数）
     * @return 分页结果
     */
    @PostMapping("/list")
    public Response<?> getReleaseList(@RequestBody ReleaseQueryDTO queryDTO) {
        return Response.success(releaseService.getReleaseList(queryDTO));
    }

    /**
     * 分页查询待审批列表
     * 仅返回状态为 PENDING 的记录
     *
     * @param queryDTO 查询条件（含分页参数）
     * @return 分页结果
     */
    @PostMapping("/todo")
    public Response<?> getTodoList(@RequestBody ReleaseQueryDTO queryDTO) {
        return Response.success(releaseService.getTodoList(queryDTO));
    }

    /**
     * 根据ID查询离监申请详情
     *
     * @param id 申请ID（路径参数）
     * @return 离监申请详情
     */
    @GetMapping("/{id}")
    public Response<?> getReleaseById(@PathVariable Long id) {
        return Response.success(releaseService.getReleaseById(id));
    }

    /**
     * 提交离监申请
     * 系统自动生成申请编号，初始状态为 PENDING
     *
     * @param dto 离监申请数据
     * @return 操作结果
     */
    @PostMapping
    public Response<?> addRelease(@RequestBody ReleaseDTO dto) {
        releaseService.addRelease(dto);
        return Response.success("申请提交成功");
    }

    /**
     * 审批通过离监申请
     * 使用乐观锁校验版本号，防止并发修改
     *
     * @param id  申请ID（路径参数）
     * @param dto 含版本号和审批人信息
     * @return 操作结果
     */
    @PostMapping("/{id}/approve")
    public Response<?> approveRelease(@PathVariable Long id, @RequestBody ReleaseDTO dto) {
        String message = releaseService.approveRelease(id, dto);
        return Response.success(message);
    }

    /**
     * 审批驳回离监申请
     * 使用乐观锁校验版本号，必须填写驳回原因
     *
     * @param id  申请ID（路径参数）
     * @param dto 含版本号和驳回原因
     * @return 操作结果
     */
    @PostMapping("/{id}/reject")
    public Response<?> rejectRelease(@PathVariable Long id, @RequestBody ReleaseDTO dto) {
        String message = releaseService.rejectRelease(id, dto);
        return Response.success(message);
    }

    /**
     * 删除离监申请
     *
     * @param id 申请ID（路径参数）
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Response<?> deleteRelease(@PathVariable Long id) {
        releaseService.deleteRelease(id);
        return Response.success("删除成功");
    }
}
