package com.example.pcim.controller;

import com.example.pcim.dto.PrisonerDTO;
import com.example.pcim.dto.PrisonerQueryDTO;
import com.example.pcim.dto.Response;
import com.example.pcim.service.PrisonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/prisoners")
public class PrisonerController {

    @Autowired
    private PrisonerService prisonerService;

    @PostMapping("/list")
    public Response<?> getPrisonerList(@RequestBody PrisonerQueryDTO queryDTO) {
        return Response.success(prisonerService.getPrisonerList(queryDTO));
    }

    @GetMapping("/{id}")
    public Response<?> getPrisonerById(@PathVariable Long id) {
        return Response.success(prisonerService.getPrisonerById(id));
    }

    @PostMapping
    public Response<?> addPrisoner(@RequestBody PrisonerDTO prisonerDTO) {
        prisonerService.addPrisoner(prisonerDTO);
        return Response.success("新增成功");
    }

    @PutMapping
    public Response<?> updatePrisoner(@RequestBody PrisonerDTO prisonerDTO) {
        prisonerService.updatePrisoner(prisonerDTO);
        return Response.success("修改成功");
    }

    @DeleteMapping("/{id}")
    public Response<?> deletePrisoner(@PathVariable Long id) {
        prisonerService.deletePrisoner(id);
        return Response.success("删除成功");
    }

    /**
     * 修改监犯风险等级
     * 前端传入 riskLevel 和 reason，后端更新并自动写入变更日志
     */
    @PostMapping("/{id}/risk-level")
    public Response<?> updateRiskLevel(@PathVariable Long id, @RequestBody Map<String, String> body) {
        prisonerService.updateRiskLevel(id, body.get("riskLevel"), body.get("reason"));
        return Response.success("风险等级变更成功");
    }
}
