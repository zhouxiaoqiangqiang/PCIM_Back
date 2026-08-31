package com.example.pcim.controller;

import com.example.pcim.dto.Response;
import com.example.pcim.dto.RoleDTO;
import com.example.pcim.dto.RoleQueryDTO;
import com.example.pcim.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统角色管理控制器
 * 提供角色的增删改查、分页查询、状态变更等 RESTful 接口
 *
 * API 基础路径：/api/roles
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    /** 角色业务服务 */
    @Autowired
    private RoleService roleService;

    /**
     * 分页查询角色列表
     *
     * @param queryDTO 查询条件（包含 keyword、status、page、size）
     * @return 分页结果，包含角色列表和总记录数
     */
    @PostMapping("/list")
    public Response<?> getRoleList(@RequestBody RoleQueryDTO queryDTO) {
        return Response.success(roleService.getRoleList(queryDTO));
    }

    /**
     * 根据ID查询角色详情
     *
     * @param id 角色主键ID
     * @return 角色详细信息
     */
    @GetMapping("/{id}")
    public Response<?> getRoleById(@PathVariable Long id) {
        return Response.success(roleService.getRoleById(id));
    }

    /**
     * 新增角色
     *
     * @param roleDTO 角色信息
     * @return 操作结果
     */
    @PostMapping
    public Response<?> addRole(@RequestBody RoleDTO roleDTO) {
        roleService.addRole(roleDTO);
        return Response.success("新增成功");
    }

    /**
     * 修改角色信息
     *
     * @param roleDTO 角色修改信息（必须包含 id 字段）
     * @return 操作结果
     */
    @PutMapping
    public Response<?> updateRole(@RequestBody RoleDTO roleDTO) {
        roleService.updateRole(roleDTO);
        return Response.success("修改成功");
    }

    /**
     * 删除角色
     * 若角色已关联用户则无法删除
     *
     * @param id 角色主键ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Response<?> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return Response.success("删除成功");
    }

    /**
     * 变更角色启用/禁用状态
     *
     * @param id     角色主键ID（路径参数）
     * @param status 目标状态：1-启用，0-禁用（查询参数）
     * @return 操作结果
     */
    @PostMapping("/{id}/status")
    public Response<?> changeRoleStatus(@PathVariable Long id, @RequestParam Integer status) {
        roleService.changeRoleStatus(id, status);
        return Response.success("状态变更成功");
    }
}
