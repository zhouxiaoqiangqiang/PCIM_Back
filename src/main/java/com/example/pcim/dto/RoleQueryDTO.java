package com.example.pcim.dto;

import lombok.Data;

/**
 * 系统角色查询数据传输对象
 * 用于前端分页查询角色列表时传递查询条件
 */
@Data
public class RoleQueryDTO {

    /** 当前页码，默认为 1 */
    private Integer page;

    /** 每页大小，默认为 10 */
    private Integer size;

    /** 关键字，用于模糊匹配角色名称和角色标识 */
    private String keyword;

    /** 角色状态：1-启用，0-禁用；不传则查询所有状态 */
    private Integer status;
}
