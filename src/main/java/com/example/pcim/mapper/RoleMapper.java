package com.example.pcim.mapper;

import com.example.pcim.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色 Mapper 接口
 * 提供对 sys_role 表的数据库操作方法
 */
@Mapper
public interface RoleMapper {

    /**
     * 根据角色ID查询角色信息
     *
     * @param id 角色主键ID
     * @return 角色实体对象，不存在时返回 null
     */
    Role selectById(@Param("id") Long id);

    /**
     * 根据角色标识查询角色信息
     * 用于新增和修改时校验 roleKey 的唯一性
     *
     * @param roleKey 角色标识（唯一）
     * @return 角色实体对象，不存在时返回 null
     */
    Role selectByKey(@Param("roleKey") String roleKey);

    /**
     * 查询角色列表（带条件过滤）
     * 支持关键字模糊匹配和状态过滤，按创建时间倒序排列
     *
     * @param keyword 关键字，模糊匹配角色名称和角色标识
     * @param status  角色状态：1-启用，0-禁用；null 则查询所有
     * @return 符合条件的角色列表
     */
    List<Role> selectList(@Param("keyword") String keyword, @Param("status") Integer status);

    /**
     * 查询符合条件的角色总记录数
     * 用于分页计算
     *
     * @param keyword 关键字，模糊匹配角色名称和角色标识
     * @param status  角色状态：1-启用，0-禁用；null 则查询所有
     * @return 符合条件的记录总数
     */
    Long selectCount(@Param("keyword") String keyword, @Param("status") Integer status);

    /**
     * 新增角色
     *
     * @param role 角色实体对象
     * @return 受影响的行数
     */
    int insert(Role role);

    /**
     * 修改角色信息
     *
     * @param role 角色实体对象（需包含 id 字段）
     * @return 受影响的行数
     */
    int update(Role role);

    /**
     * 根据角色ID删除角色
     *
     * @param id 角色主键ID
     * @return 受影响的行数
     */
    int deleteById(@Param("id") Long id);
}
