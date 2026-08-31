package com.example.pcim.service.impl;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.SentenceDTO;
import com.example.pcim.dto.SentenceQueryDTO;
import com.example.pcim.entity.Sentence;
import com.example.pcim.exception.BusinessException;
import com.example.pcim.mapper.SentenceMapper;
import com.example.pcim.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 刑期档案服务实现类
 * 负责刑期档案的增删改查及入监编号自动生成逻辑
 */
@Service
public class SentenceServiceImpl implements SentenceService {

    @Autowired
    private SentenceMapper sentenceMapper;

    /**
     * 分页查询刑期档案列表
     * 先查询全量数据再进行内存分页，与Prisoner模块保持一致
     */
    @Override
    public PageResult<Sentence> getSentenceList(SentenceQueryDTO queryDTO) {
        // 设置默认分页参数
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        // 查询列表和总数
        List<Sentence> list = sentenceMapper.selectList(
                queryDTO.getKeyword(),
                queryDTO.getCell(),
                queryDTO.getStatus(),
                queryDTO.getNearRelease()
        );
        Long total = sentenceMapper.selectCount(
                queryDTO.getKeyword(),
                queryDTO.getCell(),
                queryDTO.getStatus(),
                queryDTO.getNearRelease()
        );

        // 内存分页
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<Sentence> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    /**
     * 根据ID查询刑期档案
     */
    @Override
    public Sentence getSentenceById(Long id) {
        return sentenceMapper.selectById(id);
    }

    /**
     * 新增刑期档案
     * 若入监编号为空，则自动生成 RJ + 当年年份 + 4位流水号
     */
    @Override
    public int addSentence(SentenceDTO sentenceDTO) {
        // 如果入监编号为空，自动生成
        if (sentenceDTO.getPrisonerNo() == null || sentenceDTO.getPrisonerNo().trim().isEmpty()) {
            sentenceDTO.setPrisonerNo(generatePrisonerNo());
        } else {
            // 检查入监编号是否已存在
            Sentence existing = sentenceMapper.selectByNo(sentenceDTO.getPrisonerNo());
            if (existing != null) {
                throw new BusinessException("入监编号已存在");
            }
        }

        // DTO 转 Entity
        Sentence sentence = new Sentence();
        sentence.setPrisonerNo(sentenceDTO.getPrisonerNo());
        sentence.setName(sentenceDTO.getName());
        sentence.setCell(sentenceDTO.getCell());
        sentence.setCrimeType(sentenceDTO.getCrimeType());
        sentence.setEntryDate(sentenceDTO.getEntryDate());
        sentence.setOriginalSentence(sentenceDTO.getOriginalSentence());
        sentence.setSentenceStart(sentenceDTO.getSentenceStart());
        sentence.setReducedMonths(sentenceDTO.getReducedMonths());
        sentence.setReleaseDate(sentenceDTO.getReleaseDate());
        sentence.setParole(sentenceDTO.getParole());
        // 默认状态为"在押"
        sentence.setStatus(sentenceDTO.getStatus() != null ? sentenceDTO.getStatus() : "在押");
        sentence.setRemark(sentenceDTO.getRemark());

        return sentenceMapper.insert(sentence);
    }

    /**
     * 修改刑期档案
     */
    @Override
    public int updateSentence(SentenceDTO sentenceDTO) {
        // 先查询是否存在
        Sentence sentence = sentenceMapper.selectById(sentenceDTO.getId());
        if (sentence == null) {
            throw new BusinessException("刑期档案不存在");
        }

        // 检查入监编号是否重复（排除自身）
        Sentence existing = sentenceMapper.selectByNo(sentenceDTO.getPrisonerNo());
        if (existing != null && !existing.getId().equals(sentenceDTO.getId())) {
            throw new BusinessException("入监编号已存在");
        }

        // 更新字段
        sentence.setPrisonerNo(sentenceDTO.getPrisonerNo());
        sentence.setName(sentenceDTO.getName());
        sentence.setCell(sentenceDTO.getCell());
        sentence.setCrimeType(sentenceDTO.getCrimeType());
        sentence.setEntryDate(sentenceDTO.getEntryDate());
        sentence.setOriginalSentence(sentenceDTO.getOriginalSentence());
        sentence.setSentenceStart(sentenceDTO.getSentenceStart());
        sentence.setReducedMonths(sentenceDTO.getReducedMonths());
        sentence.setReleaseDate(sentenceDTO.getReleaseDate());
        sentence.setParole(sentenceDTO.getParole());
        sentence.setStatus(sentenceDTO.getStatus());
        sentence.setRemark(sentenceDTO.getRemark());

        return sentenceMapper.update(sentence);
    }

    /**
     * 根据ID删除刑期档案
     */
    @Override
    public int deleteSentence(Long id) {
        Sentence sentence = sentenceMapper.selectById(id);
        if (sentence == null) {
            throw new BusinessException("刑期档案不存在");
        }
        return sentenceMapper.deleteById(id);
    }

    /**
     * 自动生成入监编号
     * 格式：RJ + 4位年份 + 4位流水号（每年从0001起）
     * 例如：RJ20260001、RJ20260002
     *
     * @return 自动生成的入监编号
     */
    private String generatePrisonerNo() {
        // 获取当前年份
        String year = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));

        // 查询当年最大流水号
        Integer maxSeq = sentenceMapper.selectMaxNoByYear(year);

        // 如果没有记录，流水号从1开始；否则在最大基础上+1
        int nextSeq = (maxSeq == null ? 0 : maxSeq) + 1;

        // 拼接：RJ + 年份 + 4位补零流水号
        return "RJ" + year + String.format("%04d", nextSeq);
    }
}
