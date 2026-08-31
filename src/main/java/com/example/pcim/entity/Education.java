package com.example.pcim.entity;

import lombok.Data;

import java.util.Date;

/**
 * 教育学习实体类
 * 对应数据库表 prison_education
 */
@Data
public class Education {
    /** 主键ID */
    private Long id;

    /** 监犯编号 */
    private String prisonerNo;

    /** 监犯姓名 */
    private String name;

    /** 所在监室 */
    private String cell;

    /** 课程名称 */
    private String courseName;

    /** 教育学习日期 */
    private Date eduDate;

    /** 学习时长（单位：分钟或小时，根据业务约定） */
    private Integer duration;

    /** 授课教员 */
    private String teacher;

    /** 考核分数 */
    private Integer score;

    /** 学习结果（如：通过、未通过、优秀等） */
    private String result;

    /** 备注信息 */
    private String remark;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}
