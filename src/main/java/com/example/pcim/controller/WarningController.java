package com.example.pcim.controller;

import com.example.pcim.dto.Response;
import com.example.pcim.dto.WarningDTO;
import com.example.pcim.dto.WarningQueryDTO;
import com.example.pcim.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 预警信息 Controller
 * 提供预警管理的 RESTful API 接口
 * 基础路径: /api/warnings
 */
@RestController
@RequestMapping("/api/warnings")
public class WarningController {

    @Autowired
    private WarningService warningService;

    /**
     * 分页查询预警列表
     * POST /api/warnings/list
     *
     * @param queryDTO 查询条件（keyword, type, level, status, page, size）
     * @return 分页结果 { list: [...], total: N }
     */
    @PostMapping("/list")
    public Response<?> getWarningList(@RequestBody WarningQueryDTO queryDTO) {
        return Response.success(warningService.getWarningList(queryDTO));
    }

    /**
     * 根据ID查询预警详情
     * GET /api/warnings/{id}
     *
     * @param id 预警主键ID
     * @return 预警详情
     */
    @GetMapping("/{id}")
    public Response<?> getWarningById(@PathVariable Long id) {
        return Response.success(warningService.getWarningById(id));
    }

    /**
     * 新增预警记录
     * POST /api/warnings
     * 预警编号由后端自动生成
     *
     * @param warningDTO 预警数据
     * @return 操作结果
     */
    @PostMapping
    public Response<?> addWarning(@RequestBody WarningDTO warningDTO) {
        warningService.addWarning(warningDTO);
        return Response.success("新增预警成功");
    }

    /**
     * 更新预警记录
     * PUT /api/warnings
     *
     * @param warningDTO 预警数据（必须包含 id）
     * @return 操作结果
     */
    @PutMapping
    public Response<?> updateWarning(@RequestBody WarningDTO warningDTO) {
        warningService.updateWarning(warningDTO);
        return Response.success("更新预警成功");
    }

    /**
     * 处理预警
     * POST /api/warnings/{id}/handle
     * 将预警标记为已处理，同时记录处理人、处理时间、处理结果
     *
     * @param id  预警主键ID
     * @param dto 处理信息（status、handler、handleResult）
     * @return 操作结果
     */
    @PostMapping("/{id}/handle")
    public Response<?> handleWarning(@PathVariable Long id, @RequestBody WarningDTO dto) {
        return Response.success(warningService.handleWarning(id, dto));
    }

    /**
     * 删除预警记录
     * DELETE /api/warnings/{id}
     *
     * @param id 预警主键ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Response<?> deleteWarning(@PathVariable Long id) {
        warningService.deleteWarning(id);
        return Response.success("删除预警成功");
    }

    /**
     * 执行预警检查（模拟策略检查）
     * POST /api/warnings/check
     * 系统自动遍历在押犯人，根据随机规则生成预警记录
     *
     * @return 检查结果消息
     */
    @PostMapping("/check")
    public Response<?> checkWarnings() {
        return Response.success(warningService.checkWarnings());
    }

    /**
     * 获取未读预警信息
     * GET /api/warnings/unread-count
     * 返回结构: { unreadCount: N, list: [...] }
     *
     * @return 未读预警统计
     */
    @GetMapping("/unread-count")
    public Response<?> getUnreadWarnings() {
        return Response.success(warningService.getUnreadWarnings());
    }

    /**
     * 将单条预警标记为已读
     * POST /api/warnings/{id}/read
     *
     * @param id 预警主键ID
     * @return 操作结果
     */
    @PostMapping("/{id}/read")
    public Response<?> markWarningRead(@PathVariable Long id) {
        return Response.success(warningService.markWarningRead(id));
    }

    /**
     * 将所有预警标记为已读
     * POST /api/warnings/read-all
     *
     * @return 操作结果
     */
    @PostMapping("/read-all")
    public Response<?> markAllWarningsRead() {
        return Response.success(warningService.markAllWarningsRead());
    }
}
