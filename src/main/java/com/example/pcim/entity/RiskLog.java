package com.example.pcim.entity;

import lombok.Data;

import java.util.Date;

/**
 * 风险变更日志实体类
 */
@Data
public class RiskLog {
    /** 主键ID */
    private Long id;
    /** 监犯编号 */
    private String prisonerNo;
    /** 监犯姓名 */
    private String name;
    /** 变更前风险等级 */
    private String oldLevel;
    /** 变更后风险等级 */
    private String newLevel;
    /** 操作人 */
    private String operator;
    /** 变更原因/备注 */
    private String remark;
    /** 创建时间 */
    private Date createTime;
}
