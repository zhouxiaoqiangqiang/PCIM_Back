package com.example.pcim.mapper;

import com.example.pcim.entity.Sentence;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 刑期档案Mapper接口
 * 负责与数据库的刑期档案表进行交互
 */
@Mapper
public interface SentenceMapper {

    /**
     * 根据ID查询刑期档案
     * @param id 主键ID
     * @return 刑期档案实体
     */
    Sentence selectById(@Param("id") Long id);

    /**
     * 根据入监编号查询刑期档案
     * @param prisonerNo 入监编号
     * @return 刑期档案实体
     */
    Sentence selectByNo(@Param("prisonerNo") String prisonerNo);

    /**
     * 根据条件查询刑期档案列表
     * @param keyword 搜索关键字
     * @param cell 监室
     * @param status 状态
     * @param nearRelease 是否只返回90天内即将释放的（"1"表示是）
     * @return 刑期档案列表
     */
    List<Sentence> selectList(@Param("keyword") String keyword,
                              @Param("cell") String cell,
                              @Param("status") String status,
                              @Param("nearRelease") String nearRelease);

    /**
     * 根据条件查询刑期档案总数
     * @param keyword 搜索关键字
     * @param cell 监室
     * @param status 状态
     * @param nearRelease 是否只返回90天内即将释放的（"1"表示是）
     * @return 总数
     */
    Long selectCount(@Param("keyword") String keyword,
                     @Param("cell") String cell,
                     @Param("status") String status,
                     @Param("nearRelease") String nearRelease);

    /**
     * 新增刑期档案
     * @param sentence 刑期档案实体
     * @return 影响行数
     */
    int insert(Sentence sentence);

    /**
     * 更新刑期档案
     * @param sentence 刑期档案实体
     * @return 影响行数
     */
    int update(Sentence sentence);

    /**
     * 根据ID删除刑期档案
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 查询指定年份的入监编号最大流水号
     * 用于自动生成入监编号时的流水号递增
     * @param year 年份字符串，如 "2026"
     * @return 最大流水号（整数），若不存在则返回null
     */
    Integer selectMaxNoByYear(@Param("year") String year);
}
