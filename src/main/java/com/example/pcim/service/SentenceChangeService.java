package com.example.pcim.service;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.SentenceChangeDTO;
import com.example.pcim.dto.SentenceChangeQueryDTO;
import com.example.pcim.entity.SentenceChange;

/**
 * 刑期变动记录服务接口
 * 该模块只提供列表查询和新增功能，不允许修改和删除（录错只能新增更正记录）
 */
public interface SentenceChangeService {

    /**
     * 分页查询刑期变动记录列表
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    PageResult<SentenceChange> getSentenceChangeList(SentenceChangeQueryDTO queryDTO);

    /**
     * 新增刑期变动记录
     * @param sentenceChangeDTO 刑期变动记录数据
     * @return 影响行数
     */
    int addSentenceChange(SentenceChangeDTO sentenceChangeDTO);
}
