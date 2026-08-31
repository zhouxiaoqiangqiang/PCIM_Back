package com.example.pcim.mapper;

import com.example.pcim.entity.Education;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教育学习 Mapper 接口
 * 定义 prison_education 表的数据访问方法
 */
@Mapper
public interface EducationMapper {

    /**
     * 分页查询教育学习列表（不分页，由 Service 层内存分页）
     *
     * @param keyword 关键字（模糊匹配监犯编号、姓名、课程名称）
     * @param result  学习结果筛选
     * @return 教育学习记录列表
     */
    List<Education> selectList(@Param("keyword") String keyword,
                               @Param("result") String result);

    /**
     * 查询满足条件的总记录数
     *
     * @param keyword 关键字
     * @param result  学习结果
     * @return 总记录数
     */
    Long selectCount(@Param("keyword") String keyword,
                     @Param("result") String result);

    /**
     * 根据主键ID查询单条教育学习记录
     *
     * @param id 主键ID
     * @return 教育学习实体
     */
    Education selectById(@Param("id") Long id);

    /**
     * 新增一条教育学习记录
     *
     * @param education 教育学习实体
     * @return 受影响的行数
     */
    int insert(Education education);

    /**
     * 更新一条教育学习记录
     *
     * @param education 教育学习实体
     * @return 受影响的行数
     */
    int update(Education education);

    /**
     * 根据主键ID删除一条教育学习记录
     *
     * @param id 主键ID
     * @return 受影响的行数
     */
    int deleteById(@Param("id") Long id);
}
