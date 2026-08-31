package com.example.pcim.controller;

import com.example.pcim.dto.Response;
import com.example.pcim.dto.SentenceDTO;
import com.example.pcim.dto.SentenceQueryDTO;
import com.example.pcim.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 刑期档案控制器
 * 提供刑期档案的增删改查接口
 */
@RestController
@RequestMapping("/api/sentences")
public class SentenceController {

    @Autowired
    private SentenceService sentenceService;

    /**
     * 分页查询刑期档案列表
     * POST /api/sentences/list
     *
     * @param queryDTO 查询条件（分页参数、关键字、监室、状态、nearRelease等）
     * @return 分页结果
     */
    @PostMapping("/list")
    public Response<?> getSentenceList(@RequestBody SentenceQueryDTO queryDTO) {
        return Response.success(sentenceService.getSentenceList(queryDTO));
    }

    /**
     * 根据ID查询单个刑期档案
     * GET /api/sentences/{id}
     *
     * @param id 刑期档案主键ID
     * @return 刑期档案详情
     */
    @GetMapping("/{id}")
    public Response<?> getSentenceById(@PathVariable Long id) {
        return Response.success(sentenceService.getSentenceById(id));
    }

    /**
     * 新增刑期档案
     * POST /api/sentences
     * 如果未填入监编号，系统将自动生成
     *
     * @param sentenceDTO 刑期档案数据
     * @return 操作结果
     */
    @PostMapping
    public Response<?> addSentence(@RequestBody SentenceDTO sentenceDTO) {
        sentenceService.addSentence(sentenceDTO);
        return Response.success("新增成功");
    }

    /**
     * 修改刑期档案
     * PUT /api/sentences
     *
     * @param sentenceDTO 刑期档案数据（必须包含id）
     * @return 操作结果
     */
    @PutMapping
    public Response<?> updateSentence(@RequestBody SentenceDTO sentenceDTO) {
        sentenceService.updateSentence(sentenceDTO);
        return Response.success("修改成功");
    }

    /**
     * 删除刑期档案
     * DELETE /api/sentences/{id}
     *
     * @param id 刑期档案主键ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Response<?> deleteSentence(@PathVariable Long id) {
        sentenceService.deleteSentence(id);
        return Response.success("删除成功");
    }
}
