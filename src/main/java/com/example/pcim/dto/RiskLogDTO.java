package com.example.pcim.dto;

import lombok.Data;

/**
 * 风险变更日志数据传输对象
 */
@Data
public class RiskLogDTO {
    /** 主键ID（更新时必填） */
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
}
