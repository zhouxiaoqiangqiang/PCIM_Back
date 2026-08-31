package com.example.pcim.service;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.ReleaseDTO;
import com.example.pcim.dto.ReleaseQueryDTO;
import com.example.pcim.entity.Release;

/**
 * 离监申请 Service 接口
 * 定义离监申请相关的业务方法
 */
public interface ReleaseService {

    /**
     * 分页查询离监申请列表
     *
     * @param queryDTO 查询条件（含分页参数）
     * @return 分页结果（含列表和总数）
     */
    PageResult<Release> getReleaseList(ReleaseQueryDTO queryDTO);

    /**
     * 分页查询待审批列表
     * 仅返回状态为 PENDING 的记录
     *
     * @param queryDTO 查询条件（含分页参数）
     * @return 分页结果（含列表和总数）
     */
    PageResult<Release> getTodoList(ReleaseQueryDTO queryDTO);

    /**
     * 根据ID查询离监申请详情
     *
     * @param id 主键ID
     * @return 离监申请实体
     */
    Release getReleaseById(Long id);

    /**
     * 新增离监申请
     * 系统自动生成申请编号（格式：LQ + 年月日 + 4位随机数）
     *
     * @param dto 离监申请数据
     * @return 影响行数
     */
    int addRelease(ReleaseDTO dto);

    /**
     * 审批通过离监申请
     * 使用乐观锁校验版本号，防止并发修改
     * 状态改为 APPROVED，记录审批人和审批时间
     *
     * @param id  申请ID
     * @param dto 含版本号和审批人信息
     * @return 操作结果消息
     */
    String approveRelease(Long id, ReleaseDTO dto);

    /**
     * 审批驳回离监申请
     * 使用乐观锁校验版本号，防止并发修改
     * 状态改为 REJECTED，记录驳回原因
     *
     * @param id  申请ID
     * @param dto 含版本号和驳回原因
     * @return 操作结果消息
     */
    String rejectRelease(Long id, ReleaseDTO dto);

    /**
     * 删除离监申请
     *
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteRelease(Long id);
}
