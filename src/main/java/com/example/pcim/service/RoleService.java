package com.example.pcim.service;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.RoleDTO;
import com.example.pcim.dto.RoleQueryDTO;
import com.example.pcim.entity.Role;

/**
 * 系统角色业务服务接口
 * 定义角色管理相关的业务方法
 */
public interface RoleService {

    /**
     * 分页查询角色列表
     *
     * @param queryDTO 查询条件（包含关键字、状态、页码、每页大小）
     * @return 分页结果，包含角色列表和总记录数
     */
    PageResult<Role> getRoleList(RoleQueryDTO queryDTO);

    /**
     * 根据ID查询角色详情
     *
     * @param id 角色主键ID
     * @return 角色实体对象
     */
    Role getRoleById(Long id);

    /**
     * 新增角色
     * 需校验 roleKey 的唯一性
     *
     * @param roleDTO 角色数据传输对象
     * @return 受影响的行数
     */
    int addRole(RoleDTO roleDTO);

    /**
     * 修改角色信息
     * 需校验 roleKey 的唯一性（排除当前角色自身）
     *
     * @param roleDTO 角色数据传输对象（需包含 id）
     * @return 受影响的行数
     */
    int updateRole(RoleDTO roleDTO);

    /**
     * 删除角色
     * 若该角色已关联用户（userCount > 0），则不允许删除
     *
     * @param id 角色主键ID
     * @return 受影响的行数
     */
    int deleteRole(Long id);

    /**
     * 变更角色状态
     * 只更新 status 字段，不修改其他信息
     *
     * @param id     角色主键ID
     * @param status 目标状态：1-启用，0-禁用
     * @return 受影响的行数
     */
    int changeRoleStatus(Long id, Integer status);
}
