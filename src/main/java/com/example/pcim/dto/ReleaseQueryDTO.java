package com.example.pcim.dto;

import lombok.Data;

/**
 * 离监申请查询数据传输对象
 * 用于列表查询条件参数传递
 */
@Data
public class ReleaseQueryDTO {

    /**
     * 当前页码（从1开始，默认为1）
     */
    private Integer page;

    /**
     * 每页大小（默认为10）
     */
    private Integer size;

    /**
     * 关键字（模糊匹配申请编号、监犯编号、监犯姓名）
     */
    private String keyword;

    /**
     * 审批状态筛选（PENDING / APPROVED / REJECTED）
     */
    private String status;

    /**
     * 离监类型筛选
     */
    private String type;
}
