package com.example.pcim.service.impl;

import com.example.pcim.dto.PageResult;
import com.example.pcim.dto.RoleDTO;
import com.example.pcim.dto.RoleQueryDTO;
import com.example.pcim.entity.Role;
import com.example.pcim.exception.BusinessException;
import com.example.pcim.mapper.RoleMapper;
import com.example.pcim.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统角色业务服务实现类
 * 实现角色管理的全部业务逻辑，包括校验、分页组装、状态变更等
 */
@Service
public class RoleServiceImpl implements RoleService {

    /** 角色 Mapper 数据访问层 */
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 分页查询角色列表
     * 底层查询使用全量列表 + Java 内存分页，保持与 PrisonerServiceImpl 一致的风格
     *
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    @Override
    public PageResult<Role> getRoleList(RoleQueryDTO queryDTO) {
        // 默认页码为 1，默认每页大小为 10
        int pageNum = queryDTO.getPage() != null ? queryDTO.getPage() : 1;
        int pageSize = queryDTO.getSize() != null ? queryDTO.getSize() : 10;

        // 查询全量数据和总数
        List<Role> list = roleMapper.selectList(queryDTO.getKeyword(), queryDTO.getStatus());
        Long total = roleMapper.selectCount(queryDTO.getKeyword(), queryDTO.getStatus());

        // 使用 subList 在内存中进行分页切片
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, list.size());
        List<Role> pageList = start < list.size() ? list.subList(start, end) : list;

        return new PageResult<>(pageList, total);
    }

    /**
     * 根据ID查询角色
     *
     * @param id 角色主键ID
     * @return 角色实体
     */
    @Override
    public Role getRoleById(Long id) {
        return roleMapper.selectById(id);
    }

    /**
     * 新增角色
     * 校验逻辑：roleKey 必须全局唯一
     *
     * @param roleDTO 前端传入的角色信息
     * @return 受影响行数
     * @throws BusinessException 当 roleKey 已存在时抛出异常
     */
    @Override
    public int addRole(RoleDTO roleDTO) {
        // 校验 roleKey 唯一性
        Role existing = roleMapper.selectByKey(roleDTO.getRoleKey());
        if (existing != null) {
            throw new BusinessException("角色标识已存在");
        }

        // DTO -> Entity 转换
        Role role = new Role();
        role.setRoleName(roleDTO.getRoleName());
        role.setRoleKey(roleDTO.getRoleKey());
        role.setDescription(roleDTO.getDescription());
        role.setUserCount(0); // 新增角色默认用户数为 0
        role.setStatus(roleDTO.getStatus() != null ? roleDTO.getStatus() : 1); // 默认启用

        return roleMapper.insert(role);
    }

    /**
     * 修改角色信息
     * 校验逻辑：
     *   1. 角色必须存在
     *   2. roleKey 必须全局唯一（排除当前角色自身）
     *
     * @param roleDTO 前端传入的角色修改信息（必须包含 id）
     * @return 受影响行数
     * @throws BusinessException 当角色不存在或 roleKey 被其他角色占用时抛出异常
     */
    @Override
    public int updateRole(RoleDTO roleDTO) {
        // 校验角色是否存在
        Role role = roleMapper.selectById(roleDTO.getId());
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        // 校验 roleKey 唯一性（排除自身）
        Role existing = roleMapper.selectByKey(roleDTO.getRoleKey());
        if (existing != null && !existing.getId().equals(roleDTO.getId())) {
            throw new BusinessException("角色标识已存在");
        }

        // 覆盖更新字段（保留原有 userCount，不由前端传入）
        role.setRoleName(roleDTO.getRoleName());
        role.setRoleKey(roleDTO.getRoleKey());
        role.setDescription(roleDTO.getDescription());
        role.setStatus(roleDTO.getStatus());

        return roleMapper.update(role);
    }

    /**
     * 删除角色
     * 校验逻辑：
     *   1. 角色必须存在
     *   2. 若已关联用户（userCount > 0）则不允许删除
     *
     * @param id 角色主键ID
     * @return 受影响行数
     * @throws BusinessException 当角色不存在或已关联用户时抛出异常
     */
    @Override
    public int deleteRole(Long id) {
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        // 若角色已关联用户，禁止删除
        if (role.getUserCount() != null && role.getUserCount() > 0) {
            throw new BusinessException("该角色已关联用户，无法删除");
        }

        return roleMapper.deleteById(id);
    }

    /**
     * 变更角色状态
     * 仅更新 status 字段，不修改其他信息
     *
     * @param id     角色主键ID
     * @param status 目标状态（1-启用，0-禁用）
     * @return 受影响行数
     * @throws BusinessException 当角色不存在时抛出异常
     */
    @Override
    public int changeRoleStatus(Long id, Integer status) {
        Role role = roleMapper.selectById(id);
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        role.setStatus(status);
        return roleMapper.update(role);
    }
}
