package com.example.pcim.mapper;

import com.example.pcim.entity.Warning;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预警信息 Mapper 接口
 * 提供预警信息相关的数据库操作方法
 */
@Mapper
public interface WarningMapper {

    /**
     * 根据查询条件查询预警列表
     *
     * @param keyword 关键词（模糊匹配: 预警编号、犯人编号、犯人姓名）
     * @param type    预警类型筛选
     * @param level   预警级别筛选
     * @param status  预警状态筛选
     * @return 符合条件的预警列表
     */
    List<Warning> selectList(@Param("keyword") String keyword,
                             @Param("type") String type,
                             @Param("level") String level,
                             @Param("status") String status);

    /**
     * 根据查询条件统计预警总数
     *
     * @param keyword 关键词
     * @param type    预警类型
     * @param level   预警级别
     * @param status  预警状态
     * @return 符合条件的记录总数
     */
    Long selectCount(@Param("keyword") String keyword,
                     @Param("type") String type,
                     @Param("level") String level,
                     @Param("status") String status);

    /**
     * 根据ID查询单条预警记录
     *
     * @param id 主键ID
     * @return 预警实体
     */
    Warning selectById(@Param("id") Long id);

    /**
     * 新增预警记录
     *
     * @param warning 预警实体
     * @return 受影响行数
     */
    int insert(Warning warning);

    /**
     * 更新预警记录
     *
     * @param warning 预警实体
     * @return 受影响行数
     */
    int update(Warning warning);

    /**
     * 根据ID删除预警记录
     *
     * @param id 主键ID
     * @return 受影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 查询未读预警数量
     *
     * @return 未读预警数量
     */
    Long selectUnreadCount();

    /**
     * 查询未读预警列表（最多返回50条，按预警时间倒序）
     *
     * @return 未读预警列表
     */
    List<Warning> selectUnreadList();

    /**
     * 将单条预警标记为已读
     *
     * @param id 主键ID
     * @return 受影响行数
     */
    int markReadById(@Param("id") Long id);

    /**
     * 将所有未读预警标记为已读
     *
     * @return 受影响行数
     */
    int markAllRead();
}
