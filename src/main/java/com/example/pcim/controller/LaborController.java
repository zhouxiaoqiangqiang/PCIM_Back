package com.example.pcim.controller;

import com.example.pcim.dto.LaborDTO;
import com.example.pcim.dto.LaborQueryDTO;
import com.example.pcim.dto.Response;
import com.example.pcim.service.LaborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/labor")
public class LaborController {

    @Autowired
    private LaborService laborService;

    @PostMapping("/list")
    public Response<?> getLaborList(@RequestBody LaborQueryDTO queryDTO) {
        return Response.success(laborService.getLaborList(queryDTO));
    }

    @GetMapping("/{id}")
    public Response<?> getLaborById(@PathVariable Long id) {
        return Response.success(laborService.getLaborById(id));
    }

    @PostMapping
    public Response<?> addLabor(@RequestBody LaborDTO laborDTO) {
        laborService.addLabor(laborDTO);
        return Response.success("分配成功");
    }

    @PutMapping
    public Response<?> updateLabor(@RequestBody LaborDTO laborDTO) {
        laborService.updateLabor(laborDTO);
        return Response.success("修改成功");
    }

    @DeleteMapping("/{id}")
    public Response<?> deleteLabor(@PathVariable Long id) {
        laborService.deleteLabor(id);
        return Response.success("取消分配成功");
    }
}
