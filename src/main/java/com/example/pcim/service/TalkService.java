package com.example.pcim.service;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.TalkDTO;
import com.example.pcim.dto.TalkQueryDTO;
import com.example.pcim.entity.Talk;

/**
 * 谈话记录 Service 接口
 * 定义谈话记录模块的业务逻辑方法
 */
public interface TalkService {

    /**
     * 分页查询谈话记录列表
     *
     * @param queryDTO 查询条件（含分页参数）
     * @return 分页结果（列表 + 总条数）
     */
    PageResult<Talk> getList(TalkQueryDTO queryDTO);

    /**
     * 根据ID查询单条谈话记录
     *
     * @param id 主键ID
     * @return 谈话记录实体
     */
    Talk getById(Long id);

    /**
     * 新增一条谈话记录
     *
     * @param talkDTO 前端传入的谈话记录数据
     * @return 受影响的行数
     */
    int add(TalkDTO talkDTO);

    /**
     * 更新一条谈话记录
     *
     * @param talkDTO 前端传入的谈话记录数据（必须包含id）
     * @return 受影响的行数
     */
    int update(TalkDTO talkDTO);

    /**
     * 根据ID删除一条谈话记录
     *
     * @param id 主键ID
     * @return 受影响的行数
     */
    int delete(Long id);
}
