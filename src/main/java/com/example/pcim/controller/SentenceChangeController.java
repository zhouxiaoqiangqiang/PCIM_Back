package com.example.pcim.controller;

import com.example.pcim.dto.Response;
import com.example.pcim.dto.SentenceChangeDTO;
import com.example.pcim.dto.SentenceChangeQueryDTO;
import com.example.pcim.service.SentenceChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 刑期变动记录控制器
 * 该模块只提供列表查询和新增接口，不允许修改和删除（录错只能新增更正记录）
 */
@RestController
@RequestMapping("/api/sentence-changes")
public class SentenceChangeController {

    @Autowired
    private SentenceChangeService sentenceChangeService;

    /**
     * 分页查询刑期变动记录列表
     * POST /api/sentence-changes/list
     *
     * @param queryDTO 查询条件（分页参数、关键字、入监编号等）
     * @return 分页结果
     */
    @PostMapping("/list")
    public Response<?> getSentenceChangeList(@RequestBody SentenceChangeQueryDTO queryDTO) {
        return Response.success(sentenceChangeService.getSentenceChangeList(queryDTO));
    }

    /**
     * 新增刑期变动记录
     * POST /api/sentence-changes
     * 该模块不提供修改和删除接口，录错只能重新新增更正记录
     *
     * @param sentenceChangeDTO 刑期变动记录数据
     * @return 操作结果
     */
    @PostMapping
    public Response<?> addSentenceChange(@RequestBody SentenceChangeDTO sentenceChangeDTO) {
        sentenceChangeService.addSentenceChange(sentenceChangeDTO);
        return Response.success("新增成功");
    }
}
