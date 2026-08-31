package com.example.pcim.controller;

import com.example.pcim.dto.Response;
import com.example.pcim.dto.TalkDTO;
import com.example.pcim.dto.TalkQueryDTO;
import com.example.pcim.service.TalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 谈话记录 Controller
 * 处理谈话记录模块的 HTTP 请求，路径前缀 /api/talks
 */
@RestController
@RequestMapping("/api/talks")
public class TalkController {

    /** 注入谈话记录 Service */
    @Autowired
    private TalkService talkService;

    /**
     * 分页查询谈话记录列表
     * 请求方式：POST
     * 请求体：TalkQueryDTO JSON
     *
     * @param queryDTO 分页查询条件
     * @return 分页结果（列表 + 总条数）
     */
    @PostMapping("/list")
    public Response<?> getList(@RequestBody TalkQueryDTO queryDTO) {
        return Response.success(talkService.getList(queryDTO));
    }

    /**
     * 根据ID查询单条谈话记录
     * 请求方式：GET
     * 路径参数：id - 谈话记录主键ID
     *
     * @param id 主键ID
     * @return 谈话记录实体
     */
    @GetMapping("/{id}")
    public Response<?> getById(@PathVariable Long id) {
        return Response.success(talkService.getById(id));
    }

    /**
     * 新增一条谈话记录
     * 请求方式：POST
     * 请求体：TalkDTO JSON
     *
     * @param talkDTO 谈话记录数据
     * @return 操作提示
     */
    @PostMapping
    public Response<?> add(@RequestBody TalkDTO talkDTO) {
        talkService.add(talkDTO);
        return Response.success("新增成功");
    }

    /**
     * 更新一条谈话记录
     * 请求方式：PUT
     * 请求体：TalkDTO JSON（必须包含 id 字段）
     *
     * @param talkDTO 谈话记录数据
     * @return 操作提示
     */
    @PutMapping
    public Response<?> update(@RequestBody TalkDTO talkDTO) {
        talkService.update(talkDTO);
        return Response.success("修改成功");
    }

    /**
     * 根据ID删除一条谈话记录
     * 请求方式：DELETE
     * 路径参数：id - 谈话记录主键ID
     *
     * @param id 主键ID
     * @return 操作提示
     */
    @DeleteMapping("/{id}")
    public Response<?> delete(@PathVariable Long id) {
        talkService.delete(id);
        return Response.success("删除成功");
    }
}
