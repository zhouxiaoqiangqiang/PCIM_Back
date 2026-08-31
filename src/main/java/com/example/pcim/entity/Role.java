package com.example.pcim.entity;

import lombok.Data;

import java.util.Date;

/**
 * 系统角色实体类
 * 对应数据库表 sys_role
 */
@Data
public class Role {

    /** 角色主键ID */
    private Long id;

    /** 角色名称，如：管理员、普通用户 */
    private String roleName;

    /** 角色标识（唯一），用于权限控制，如：admin、user */
    private String roleKey;

    /** 角色描述，用于说明角色用途 */
    private String description;

    /** 已关联该角色的用户数量 */
    private Integer userCount;

    /** 角色状态：1-启用，0-禁用 */
    private Integer status;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}
