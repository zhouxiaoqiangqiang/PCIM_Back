package com.example.pcim.dto;

import lombok.Data;

import java.util.Date;

/**
 * 探视登记数据传输对象
 * 用于前后端交互时传递探视登记的业务数据
 */
@Data
public class VisitDTO {
    /** 主键ID（更新时必传） */
    private Long id;

    /** 监犯编号 */
    private String prisonerNo;

    /** 监犯姓名 */
    private String name;

    /** 所在监室 */
    private String cell;

    /** 探视人姓名 */
    private String visitorName;

    /** 探视人身份证号 */
    private String visitorIdCard;

    /** 与监犯关系 */
    private String relation;

    /** 探视时间 */
    private Date visitTime;

    /** 备注信息 */
    private String remark;
}
