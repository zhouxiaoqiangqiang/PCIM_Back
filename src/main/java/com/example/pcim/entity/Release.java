package com.example.pcim.entity;

import lombok.Data;

import java.util.Date;

/**
 * 离监申请实体类
 * 对应数据库表：prison_release
 */
@Data
public class Release {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 申请编号（自动生成，格式：LQ + 年月日 + 4位随机数）
     */
    private String applyNo;

    /**
     * 监犯编号
     */
    private String prisonerNo;

    /**
     * 监犯姓名
     */
    private String name;

    /**
     * 所在监室
     */
    private String cell;

    /**
     * 离监类型（如：探亲、就医、办案等）
     */
    private String type;

    /**
     * 离监原因
     */
    private String reason;

    /**
     * 审批状态（PENDING：待审批；APPROVED：已通过；REJECTED：已驳回）
     */
    private String status;

    /**
     * 审批人
     */
    private String approver;

    /**
     * 审批时间
     */
    private Date approveTime;

    /**
     * 驳回原因
     */
    private String rejectReason;

    /**
     * 乐观锁版本号（每次更新自动+1）
     */
    private Integer version;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 记录更新时间
     */
    private Date updateTime;
}
