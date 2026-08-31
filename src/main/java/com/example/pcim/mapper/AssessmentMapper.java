package com.example.pcim.mapper;

import com.example.pcim.entity.Assessment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 考核评估 Mapper 接口
 * 定义 prison_assessment 表的数据访问方法
 */
@Mapper
public interface AssessmentMapper {

    /**
     * 分页查询考核评估列表（不分页，由 Service 层内存分页）
     *
     * @param keyword 关键字（模糊匹配监犯编号、姓名、考核项目）
     * @param type    考核类型筛选
     * @param date    考核日期筛选
     * @return 考核评估记录列表
     */
    List<Assessment> selectList(@Param("keyword") String keyword,
                                @Param("type") String type,
                                @Param("date") String date);

    /**
     * 查询满足条件的总记录数
     *
     * @param keyword 关键字
     * @param type    考核类型
     * @param date    考核日期
     * @return 总记录数
     */
    Long selectCount(@Param("keyword") String keyword,
                     @Param("type") String type,
                     @Param("date") String date);

    /**
     * 根据主键ID查询单条考核评估记录
     *
     * @param id 主键ID
     * @return 考核评估实体
     */
    Assessment selectById(@Param("id") Long id);

    /**
     * 新增一条考核评估记录
     *
     * @param assessment 考核评估实体
     * @return 受影响的行数
     */
    int insert(Assessment assessment);

    /**
     * 更新一条考核评估记录
     *
     * @param assessment 考核评估实体
     * @return 受影响的行数
     */
    int update(Assessment assessment);

    /**
     * 根据主键ID删除一条考核评估记录
     *
     * @param id 主键ID
     * @return 受影响的行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 删除指定月份的月度汇总记录
     * 用于月度汇总执行前清理旧的汇总数据，保证同月重复执行不会产生重复记录
     *
     * @param yearMonth 年月字符串，格式为 yyyy-MM
     * @return 受影响的行数
     */
    int deleteSummaryByMonth(@Param("yearMonth") String yearMonth);

    /**
     * 批量插入考核评估记录
     * 用于月度汇总时一次性插入多条汇总记录
     *
     * @param list 考核评估记录列表
     * @return 受影响的行数
     */
    int batchInsert(List<Assessment> list);
}
