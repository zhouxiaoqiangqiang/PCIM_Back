package com.example.pcim.dto;

import lombok.Data;

/**
 * 系统角色数据传输对象
 * 用于接收前端提交的新增/修改角色请求参数
 */
@Data
public class RoleDTO {

    /** 角色主键ID（修改时必传，新增时不传） */
    private Long id;

    /** 角色名称，如：管理员、普通用户 */
    private String roleName;

    /** 角色标识（唯一），用于权限控制，如：admin、user */
    private String roleKey;

    /** 角色描述，用于说明角色用途 */
    private String description;

    /** 已关联该角色的用户数量（新增/修改时不传，由系统维护） */
    private Integer userCount;

    /** 角色状态：1-启用，0-禁用 */
    private Integer status;
}
