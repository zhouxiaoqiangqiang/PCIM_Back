package com.example.pcim.service;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.VisitDTO;
import com.example.pcim.dto.VisitQueryDTO;
import com.example.pcim.entity.Visit;

/**
 * 探视登记 Service 接口
 * 定义探视登记模块的业务逻辑方法
 */
public interface VisitService {

    /**
     * 分页查询探视登记列表
     *
     * @param queryDTO 查询条件（含分页参数）
     * @return 分页结果（列表 + 总条数）
     */
    PageResult<Visit> getList(VisitQueryDTO queryDTO);

    /**
     * 根据ID查询单条探视登记
     *
     * @param id 主键ID
     * @return 探视登记实体
     */
    Visit getById(Long id);

    /**
     * 新增一条探视登记
     *
     * @param visitDTO 前端传入的探视登记数据
     * @return 受影响的行数
     */
    int add(VisitDTO visitDTO);

    /**
     * 更新一条探视登记
     *
     * @param visitDTO 前端传入的探视登记数据（必须包含id）
     * @return 受影响的行数
     */
    int update(VisitDTO visitDTO);

    /**
     * 根据ID删除一条探视登记
     *
     * @param id 主键ID
     * @return 受影响的行数
     */
    int delete(Long id);
}
