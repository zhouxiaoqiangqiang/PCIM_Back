package com.example.pcim.mapper;

import com.example.pcim.entity.Visit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 探视登记 Mapper 接口
 * 定义 prison_visit 表的数据访问方法
 */
@Mapper
public interface VisitMapper {

    /**
     * 查询探视登记列表（不分页，由 Service 层内存分页）
     *
     * @param keyword  关键字（模糊匹配监犯编号、姓名、探视人姓名）
     * @param relation 与监犯关系筛选
     * @param date     探视日期（等于条件，格式：yyyy-MM-dd）
     * @return 探视登记列表
     */
    List<Visit> selectList(@Param("keyword") String keyword,
                           @Param("relation") String relation,
                           @Param("date") String date);

    /**
     * 查询满足条件的总记录数
     *
     * @param keyword  关键字
     * @param relation 与监犯关系
     * @param date     探视日期
     * @return 总记录数
     */
    Long selectCount(@Param("keyword") String keyword,
                     @Param("relation") String relation,
                     @Param("date") String date);

    /**
     * 根据主键ID查询单条探视登记
     *
     * @param id 主键ID
     * @return 探视登记实体
     */
    Visit selectById(@Param("id") Long id);

    /**
     * 新增一条探视登记
     *
     * @param visit 探视登记实体
     * @return 受影响的行数
     */
    int insert(Visit visit);

    /**
     * 更新一条探视登记
     *
     * @param visit 探视登记实体
     * @return 受影响的行数
     */
    int update(Visit visit);

    /**
     * 根据主键ID删除一条探视登记
     *
     * @param id 主键ID
     * @return 受影响的行数
     */
    int deleteById(@Param("id") Long id);
}
