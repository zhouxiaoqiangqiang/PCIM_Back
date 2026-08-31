package com.example.pcim.dto;

import lombok.Data;

import java.util.Date;

/**
 * 教育学习数据传输对象
 * 用于前后端交互时传递教育学习的业务数据
 */
@Data
public class EducationDTO {
    /** 主键ID（更新时必传） */
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

    /** 学习时长 */
    private Integer duration;

    /** 授课教员 */
    private String teacher;

    /** 考核分数 */
    private Integer score;

    /** 学习结果 */
    private String result;

    /** 备注信息 */
    private String remark;
}
