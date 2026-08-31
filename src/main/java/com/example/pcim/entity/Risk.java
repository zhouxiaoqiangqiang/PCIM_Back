package com.example.pcim.entity;

import lombok.Data;

import java.util.Date;

/**
 * 风险评估实体类
 */
@Data
public class Risk {
    /** 主键ID */
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
    /** 总分 */
    private Integer totalScore;
    /** 风险等级（高风险/中风险/低风险） */
    private String riskLevel;
    /** 评估人 */
    private String assessor;
    /** 备注 */
    private String remark;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;
}
