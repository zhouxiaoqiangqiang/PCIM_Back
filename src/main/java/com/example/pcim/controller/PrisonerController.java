package com.example.pcim.controller;

import com.example.pcim.dto.PrisonerDTO;
import com.example.pcim.dto.PrisonerQueryDTO;
import com.example.pcim.dto.Response;
import com.example.pcim.service.PrisonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
