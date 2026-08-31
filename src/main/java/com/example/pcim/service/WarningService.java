package com.example.pcim.service;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.WarningDTO;
import com.example.pcim.dto.WarningQueryDTO;
import com.example.pcim.entity.Warning;

/**
 * 预警信息 Service 接口
 * 定义预警信息相关的业务方法
 */
public interface WarningService {

    /**
     * 分页查询预警列表
     *
     * @param queryDTO 查询条件（包含关键词、类型、级别、状态、分页参数）
     * @return 分页结果（包含列表和总数）
     */
    PageResult<Warning> getWarningList(WarningQueryDTO queryDTO);

    /**
     * 根据ID查询单条预警详情
     *
     * @param id 预警主键ID
     * @return 预警实体
     */
    Warning getWarningById(Long id);

    /**
     * 新增预警记录
     * 系统自动生成预警编号（格式: YJ + 年月日 + 4位随机数）
     *
     * @param warningDTO 预警数据
     * @return 受影响行数
     */
    int addWarning(WarningDTO warningDTO);

    /**
     * 更新预警记录
     *
     * @param warningDTO 预警数据（必须包含 id 字段）
     * @return 受影响行数
     */
    int updateWarning(WarningDTO warningDTO);

    /**
     * 处理预警
     * 将预警状态更新为指定状态，并记录处理人、处理时间、处理结果
     *
     * @param id  预警主键ID
     * @param dto 处理信息（包含 status、handler、handleResult）
     * @return 处理结果消息
     */
    String handleWarning(Long id, WarningDTO dto);

    /**
     * 根据ID删除预警记录
     *
     * @param id 预警主键ID
     * @return 受影响行数
     */
    int deleteWarning(Long id);

    /**
     * 执行预警检查（模拟策略检查）
     * 遍历在押犯人，随机生成预警记录
     * 实际生产环境可替换为真实规则引擎
     *
     * @return 检查结果消息（包含生成的预警数量）
     */
    String checkWarnings();

    /**
     * 获取未读预警信息
     * 返回结构: { unreadCount: N, list: [...] }
     *
     * @return 未读预警统计信息
     */
    Object getUnreadWarnings();

    /**
     * 将单条预警标记为已读
     *
     * @param id 预警主键ID
     * @return 操作结果消息
     */
    String markWarningRead(Long id);

    /**
     * 将所有预警标记为已读
     *
     * @return 操作结果消息
     */
    String markAllWarningsRead();
}
