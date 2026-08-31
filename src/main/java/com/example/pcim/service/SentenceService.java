package com.example.pcim.service;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.SentenceDTO;
import com.example.pcim.dto.SentenceQueryDTO;
import com.example.pcim.entity.Sentence;

/**
 * 刑期档案服务接口
 * 定义刑期档案相关的业务方法
 */
public interface SentenceService {

    /**
     * 分页查询刑期档案列表
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    PageResult<Sentence> getSentenceList(SentenceQueryDTO queryDTO);

    /**
     * 根据ID查询刑期档案
     * @param id 主键ID
     * @return 刑期档案实体
     */
    Sentence getSentenceById(Long id);

    /**
     * 新增刑期档案
     * 若入监编号为空则自动生成（RJ + 年份 + 4位流水号）
     * @param sentenceDTO 刑期档案数据
     * @return 影响行数
     */
    int addSentence(SentenceDTO sentenceDTO);

    /**
     * 修改刑期档案
     * @param sentenceDTO 刑期档案数据
     * @return 影响行数
     */
    int updateSentence(SentenceDTO sentenceDTO);

    /**
     * 根据ID删除刑期档案
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteSentence(Long id);
}
