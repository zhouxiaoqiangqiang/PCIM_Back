package com.example.pcim.dto;

import lombok.Data;

/**
 * 离监申请数据传输对象
 * 用于前端与后端之间的数据交互
 */
@Data
public class ReleaseDTO {

    /**
     * 主键ID（更新/审批时必填）
     */
    private Long id;

    /**
     * 申请编号（新增时自动生成，前端无需传入）
     */
    private String applyNo;

    /**
     * 监犯编号（新增时必填）
     */
    private String prisonerNo;

    /**
     * 监犯姓名（新增时必填）
     */
    private String name;

    /**
     * 所在监室（新增时必填）
     */
    private String cell;

    /**
     * 离监类型（新增时必填）
     */
    private String type;

    /**
     * 离监原因（新增时必填）
     */
    private String reason;

    /**
     * 审批人（审批时由系统或前端传入）
     */
    private String approver;

    /**
     * 驳回原因（驳回时必填）
     */
    private String rejectReason;

    /**
     * 乐观锁版本号（审批时用于校验，防止并发修改）
     */
    private Integer version;
}
