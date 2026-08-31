package com.example.pcim.mapper;

import com.example.pcim.entity.SentenceChange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 刑期变动记录Mapper接口
 * 该模块只允许新增和查询，不提供修改和删除方法
 */
@Mapper
public interface SentenceChangeMapper {

    /**
     * 根据ID查询刑期变动记录
     * @param id 主键ID
     * @return 刑期变动记录实体
     */
    SentenceChange selectById(@Param("id") Long id);

    /**
     * 根据条件查询刑期变动记录列表
     * @param keyword 搜索关键字（匹配姓名或入监编号）
     * @param prisonerNo 入监编号精确筛选
     * @return 刑期变动记录列表
     */
    List<SentenceChange> selectList(@Param("keyword") String keyword,
                                    @Param("prisonerNo") String prisonerNo);

    /**
     * 根据条件查询刑期变动记录总数
     * @param keyword 搜索关键字
     * @param prisonerNo 入监编号精确筛选
     * @return 总数
     */
    Long selectCount(@Param("keyword") String keyword,
                     @Param("prisonerNo") String prisonerNo);

    /**
     * 新增刑期变动记录
     * @param sentenceChange 刑期变动记录实体
     * @return 影响行数
     */
    int insert(SentenceChange sentenceChange);
}
