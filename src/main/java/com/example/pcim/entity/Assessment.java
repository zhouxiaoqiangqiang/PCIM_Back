package com.example.pcim.entity;

import lombok.Data;

import java.util.Date;

/**
 * 考核评估实体类
 * 对应数据库表 prison_assessment
 */
@Data
public class Assessment {
    /** 主键ID */
    private Long id;

    /** 监犯编号 */
    private String prisonerNo;

    /** 监犯姓名 */
    private String name;

    /** 所在监室 */
    private String cell;

    /** 考核日期 */
    private Date assessDate;

    /** 考核类型（如：日常考核、月度汇总、季度考核等） */
    private String type;

    /** 考核项目 */
    private String item;

    /** 考核分值 */
    private Integer score;

    /** 考核等级 */
    private String level;

    /** 考核经办人 */
    private String handler;

    /** 备注信息 */
    private String remark;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}
