package com.example.pcim.service.impl;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.VisitDTO;
import com.example.pcim.dto.VisitQueryDTO;
import com.example.pcim.entity.Visit;
import com.example.pcim.exception.BusinessException;
import com.example.pcim.mapper.VisitMapper;
import com.example.pcim.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 探视登记 Service 实现类
 * 实现探视登记模块的业务逻辑，包括分页查询、CRUD 等
 */
@Service
public class VisitServiceImpl implements VisitService {

    /** 注入探视登记 Mapper */
    @Autowired
    private VisitMapper visitMapper;

    @Override
    public PageResult<Visit> getList(VisitQueryDTO queryDTO) {
        // 处理分页参数，默认第1页、每页10条
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        // 查询全部符合条件的记录（由 Mapper 排序好）
        List<Visit> list = visitMapper.selectList(
                queryDTO.getKeyword(), queryDTO.getRelation(), queryDTO.getDate());
        // 查询总记录数
        Long total = visitMapper.selectCount(
                queryDTO.getKeyword(), queryDTO.getRelation(), queryDTO.getDate());

        // 内存分页（与其他 Service 保持一致）
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<Visit> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    @Override
    public Visit getById(Long id) {
        return visitMapper.selectById(id);
    }

    @Override
    public int add(VisitDTO visitDTO) {
        // 将 DTO 转换为 Entity
        Visit visit = convertDtoToEntity(visitDTO);
        return visitMapper.insert(visit);
    }

    @Override
    public int update(VisitDTO visitDTO) {
        // 更新前先校验记录是否存在
        Visit existing = visitMapper.selectById(visitDTO.getId());
        if (existing == null) {
            throw new BusinessException("探视记录不存在");
        }

        Visit visit = convertDtoToEntity(visitDTO);
        return visitMapper.update(visit);
    }

    @Override
    public int delete(Long id) {
        // 删除前先校验记录是否存在
        Visit existing = visitMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException("探视记录不存在");
        }
        return visitMapper.deleteById(id);
    }

    /**
     * DTO 转 Entity 的私有工具方法
     *
     * @param dto 数据传输对象
     * @return 探视登记实体
     */
    private Visit convertDtoToEntity(VisitDTO dto) {
        Visit visit = new Visit();
        visit.setId(dto.getId());
        visit.setPrisonerNo(dto.getPrisonerNo());
        visit.setName(dto.getName());
        visit.setCell(dto.getCell());
        visit.setVisitorName(dto.getVisitorName());
        visit.setVisitorIdCard(dto.getVisitorIdCard());
        visit.setRelation(dto.getRelation());
        visit.setVisitTime(dto.getVisitTime());
        visit.setRemark(dto.getRemark());
        return visit;
    }
}
