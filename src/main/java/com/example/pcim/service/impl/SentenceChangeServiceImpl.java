package com.example.pcim.service.impl;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.SentenceChangeDTO;
import com.example.pcim.dto.SentenceChangeQueryDTO;
import com.example.pcim.entity.SentenceChange;
import com.example.pcim.mapper.SentenceChangeMapper;
import com.example.pcim.service.SentenceChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 刑期变动记录服务实现类
 * 只实现列表查询和新增，不提供修改和删除操作
 */
@Service
public class SentenceChangeServiceImpl implements SentenceChangeService {

    @Autowired
    private SentenceChangeMapper sentenceChangeMapper;

    /**
     * 分页查询刑期变动记录列表
     * 先查询全量数据再进行内存分页
     */
    @Override
    public PageResult<SentenceChange> getSentenceChangeList(SentenceChangeQueryDTO queryDTO) {
        // 设置默认分页参数
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        // 查询列表和总数
        List<SentenceChange> list = sentenceChangeMapper.selectList(
                queryDTO.getKeyword(),
                queryDTO.getPrisonerNo()
        );
        Long total = sentenceChangeMapper.selectCount(
                queryDTO.getKeyword(),
                queryDTO.getPrisonerNo()
        );

        // 内存分页
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<SentenceChange> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    /**
     * 新增刑期变动记录
     * 该模块只允许新增，录错只能重新新增更正记录
     */
    @Override
    public int addSentenceChange(SentenceChangeDTO sentenceChangeDTO) {
        // DTO 转 Entity
        SentenceChange sentenceChange = new SentenceChange();
        sentenceChange.setPrisonerNo(sentenceChangeDTO.getPrisonerNo());
        sentenceChange.setName(sentenceChangeDTO.getName());
        sentenceChange.setChangeType(sentenceChangeDTO.getChangeType());
        sentenceChange.setOldValue(sentenceChangeDTO.getOldValue());
        sentenceChange.setNewValue(sentenceChangeDTO.getNewValue());
        sentenceChange.setChangeDate(sentenceChangeDTO.getChangeDate());
        sentenceChange.setOperator(sentenceChangeDTO.getOperator());
        sentenceChange.setReason(sentenceChangeDTO.getReason());
        sentenceChange.setRemark(sentenceChangeDTO.getRemark());

        return sentenceChangeMapper.insert(sentenceChange);
    }
}
