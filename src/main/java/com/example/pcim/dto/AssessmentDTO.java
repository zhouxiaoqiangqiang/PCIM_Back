package com.example.pcim.dto;

import lombok.Data;

import java.util.Date;

/**
 * 考核评估数据传输对象
 * 用于前后端交互时传递考核评估的业务数据
 */
@Data
public class AssessmentDTO {
    /** 主键ID（更新时必传） */
    private Long id;

    /** 监犯编号 */
    private String prisonerNo;

    /** 监犯姓名 */
    private String name;

    /** 所在监室 */
    private String cell;

    /** 考核日期 */
    private Date assessDate;

    /** 考核类型 */
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
}
