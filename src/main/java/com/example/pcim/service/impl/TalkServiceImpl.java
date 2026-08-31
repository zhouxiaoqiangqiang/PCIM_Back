package com.example.pcim.service.impl;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.TalkDTO;
import com.example.pcim.dto.TalkQueryDTO;
import com.example.pcim.entity.Talk;
import com.example.pcim.exception.BusinessException;
import com.example.pcim.mapper.TalkMapper;
import com.example.pcim.service.TalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 谈话记录 Service 实现类
 * 实现谈话记录模块的业务逻辑，包括分页查询、CRUD 等
 */
@Service
public class TalkServiceImpl implements TalkService {

    /** 注入谈话记录 Mapper */
    @Autowired
    private TalkMapper talkMapper;

    @Override
    public PageResult<Talk> getList(TalkQueryDTO queryDTO) {
        // 处理分页参数，默认第1页、每页10条
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        // 查询全部符合条件的记录（由 Mapper 排序好）
        List<Talk> list = talkMapper.selectList(
                queryDTO.getKeyword(), queryDTO.getType());
        // 查询总记录数
        Long total = talkMapper.selectCount(
                queryDTO.getKeyword(), queryDTO.getType());

        // 内存分页（与其他 Service 保持一致）
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<Talk> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    @Override
    public Talk getById(Long id) {
        return talkMapper.selectById(id);
    }

    @Override
    public int add(TalkDTO talkDTO) {
        // 将 DTO 转换为 Entity
        Talk talk = convertDtoToEntity(talkDTO);
        return talkMapper.insert(talk);
    }

    @Override
    public int update(TalkDTO talkDTO) {
        // 更新前先校验记录是否存在
        Talk existing = talkMapper.selectById(talkDTO.getId());
        if (existing == null) {
            throw new BusinessException("谈话记录不存在");
        }

        Talk talk = convertDtoToEntity(talkDTO);
        return talkMapper.update(talk);
    }

    @Override
    public int delete(Long id) {
        // 删除前先校验记录是否存在
        Talk existing = talkMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException("谈话记录不存在");
        }
        return talkMapper.deleteById(id);
    }

    /**
     * DTO 转 Entity 的私有工具方法
     *
     * @param dto 数据传输对象
     * @return 谈话记录实体
     */
    private Talk convertDtoToEntity(TalkDTO dto) {
        Talk talk = new Talk();
        talk.setId(dto.getId());
        talk.setPrisonerNo(dto.getPrisonerNo());
        talk.setName(dto.getName());
        talk.setCell(dto.getCell());
        talk.setTalkDate(dto.getTalkDate());
        talk.setType(dto.getType());
        talk.setLocation(dto.getLocation());
        talk.setContent(dto.getContent());
        talk.setTalker(dto.getTalker());
        talk.setFollowUp(dto.getFollowUp());
        talk.setRemark(dto.getRemark());
        return talk;
    }
}
