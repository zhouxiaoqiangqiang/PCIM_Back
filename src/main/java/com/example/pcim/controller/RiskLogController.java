package com.example.pcim.controller;

import com.example.pcim.dto.Response;
import com.example.pcim.dto.RiskLogDTO;
import com.example.pcim.dto.RiskLogQueryDTO;
import com.example.pcim.service.RiskLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 风险变更日志 Controller
 */
@RestController
@RequestMapping("/api/risk-logs")
public class RiskLogController {

    @Autowired
    private RiskLogService riskLogService;

    /**
     * 分页查询风险变更日志列表
     */
    @PostMapping("/list")
    public Response<?> getRiskLogList(@RequestBody RiskLogQueryDTO queryDTO) {
        return Response.success(riskLogService.getRiskLogList(queryDTO));
    }

    /**
     * 根据ID查询风险变更日志
     */
    @GetMapping("/{id}")
    public Response<?> getRiskLogById(@PathVariable Long id) {
        return Response.success(riskLogService.getRiskLogById(id));
    }

    /**
     * 新增风险变更日志
     */
    @PostMapping
    public Response<?> addRiskLog(@RequestBody RiskLogDTO riskLogDTO) {
        riskLogService.addRiskLog(riskLogDTO);
        return Response.success("新增成功");
    }

    /**
     * 更新风险变更日志
     */
    @PutMapping
    public Response<?> updateRiskLog(@RequestBody RiskLogDTO riskLogDTO) {
        riskLogService.updateRiskLog(riskLogDTO);
        return Response.success("修改成功");
    }

    /**
     * 删除风险变更日志
     */
    @DeleteMapping("/{id}")
    public Response<?> deleteRiskLog(@PathVariable Long id) {
        riskLogService.deleteRiskLog(id);
        return Response.success("删除成功");
    }
}
