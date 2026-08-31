package com.example.pcim.mapper;

import com.example.pcim.entity.Release;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 离监申请 Mapper 接口
 * 提供离监申请数据的持久化操作
 */
@Mapper
public interface ReleaseMapper {

    /**
     * 根据ID查询离监申请
     *
     * @param id 主键ID
     * @return 离监申请实体
     */
    Release selectById(@Param("id") Long id);

    /**
     * 条件查询离监申请列表（分页前的全量列表）
     * keyword模糊匹配：apply_no / prisoner_no / name
     * 支持 status 和 type 过滤
     * 按 apply_time 倒序排列
     *
     * @param keyword 关键字
     * @param status  审批状态
     * @param type    离监类型
     * @return 离监申请列表
     */
    List<Release> selectList(@Param("keyword") String keyword,
                             @Param("status") String status,
                             @Param("type") String type);

    /**
     * 条件查询离监申请总数
     *
     * @param keyword 关键字
     * @param status  审批状态
     * @param type    离监类型
     * @return 记录总数
     */
    Long selectCount(@Param("keyword") String keyword,
                     @Param("status") String status,
                     @Param("type") String type);

    /**
     * 查询待审批列表（status = 'PENDING'，按 apply_time 倒序）
     *
     * @return 待审批离监申请列表
     */
    List<Release> selectTodoList();

    /**
     * 查询待审批数量
     *
     * @return 待审批记录数
     */
    Long selectTodoCount();

    /**
     * 新增离监申请
     *
     * @param release 离监申请实体
     * @return 影响行数
     */
    int insert(Release release);

    /**
     * 更新离监申请（version字段自动+1）
     *
     * @param release 离监申请实体
     * @return 影响行数
     */
    int update(Release release);

    /**
     * 根据ID删除离监申请
     *
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
}
