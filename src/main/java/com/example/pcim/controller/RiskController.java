package com.example.pcim.controller;

import com.example.pcim.dto.Response;
import com.example.pcim.dto.RiskDTO;
import com.example.pcim.dto.RiskQueryDTO;
import com.example.pcim.service.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 风险评估 Controller
 */
@RestController
@RequestMapping("/api/risks")
public class RiskController {

    @Autowired
    private RiskService riskService;

    /**
     * 分页查询风险评估列表
     */
    @PostMapping("/list")
    public Response<?> getRiskList(@RequestBody RiskQueryDTO queryDTO) {
        return Response.success(riskService.getRiskList(queryDTO));
    }

    /**
     * 根据ID查询风险评估
     */
    @GetMapping("/{id}")
    public Response<?> getRiskById(@PathVariable Long id) {
        return Response.success(riskService.getRiskById(id));
    }

    /**
     * 新增风险评估
     */
    @PostMapping
    public Response<?> addRisk(@RequestBody RiskDTO riskDTO) {
        riskService.addRisk(riskDTO);
        return Response.success("新增成功");
    }

    /**
     * 更新风险评估
     */
    @PutMapping
    public Response<?> updateRisk(@RequestBody RiskDTO riskDTO) {
        riskService.updateRisk(riskDTO);
        return Response.success("修改成功");
    }

    /**
     * 删除风险评估
     */
    @DeleteMapping("/{id}")
    public Response<?> deleteRisk(@PathVariable Long id) {
        riskService.deleteRisk(id);
        return Response.success("删除成功");
    }
}
