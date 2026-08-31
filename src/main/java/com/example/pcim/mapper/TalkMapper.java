package com.example.pcim.mapper;

import com.example.pcim.entity.Talk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 谈话记录 Mapper 接口
 * 定义 prison_talk 表的数据访问方法
 */
@Mapper
public interface TalkMapper {

    /**
     * 查询谈话记录列表（不分页，由 Service 层内存分页）
     *
     * @param keyword 关键字（模糊匹配监犯编号、姓名、谈话内容）
     * @param type    谈话类型筛选
     * @return 谈话记录列表
     */
    List<Talk> selectList(@Param("keyword") String keyword,
                          @Param("type") String type);

    /**
     * 查询满足条件的总记录数
     *
     * @param keyword 关键字
     * @param type    谈话类型
     * @return 总记录数
     */
    Long selectCount(@Param("keyword") String keyword,
                     @Param("type") String type);

    /**
     * 根据主键ID查询单条谈话记录
     *
     * @param id 主键ID
     * @return 谈话记录实体
     */
    Talk selectById(@Param("id") Long id);

    /**
     * 新增一条谈话记录
     *
     * @param talk 谈话记录实体
     * @return 受影响的行数
     */
    int insert(Talk talk);

    /**
     * 更新一条谈话记录
     *
     * @param talk 谈话记录实体
     * @return 受影响的行数
     */
    int update(Talk talk);

    /**
     * 根据主键ID删除一条谈话记录
     *
     * @param id 主键ID
     * @return 受影响的行数
     */
    int deleteById(@Param("id") Long id);
}
