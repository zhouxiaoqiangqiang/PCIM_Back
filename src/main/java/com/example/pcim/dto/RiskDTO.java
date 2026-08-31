package com.example.pcim.dto;

import lombok.Data;

import java.util.Date;

/**
 * 风险评估数据传输对象
 */
@Data
public class RiskDTO {
    /** 主键ID（更新时必填） */
    private Long id;
    /** 监犯编号 */
    private String prisonerNo;
    /** 监犯姓名 */
    private String name;
    /** 所在监室 */
    private String cell;
    /** 罪名类型 */
    private String crimeType;
    /** 评估日期 */
    private Date assessDate;
    /** 暴力倾向得分 */
    private Integer violence;
    /** 逃跑倾向得分 */
    private Integer escape;
    /** 自杀倾向得分 */
    private Integer suicide;
    /** 健康状况得分 */
    private Integer health;
    /** 总分（由后端自动计算） */
    private Integer totalScore;
    /** 风险等级（由后端自动计算） */
    private String riskLevel;
    /** 评估人 */
    private String assessor;
    /** 备注 */
    private String remark;
}
