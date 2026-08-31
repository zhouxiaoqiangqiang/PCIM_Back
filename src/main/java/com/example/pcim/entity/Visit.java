package com.example.pcim.entity;

import lombok.Data;

import java.util.Date;

/**
 * 探视登记实体类
 * 对应数据库表 prison_visit
 */
@Data
public class Visit {
    /** 主键ID */
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

    /** 与监犯关系（如：父母、配偶、子女、亲友等） */
    private String relation;

    /** 探视时间 */
    private Date visitTime;

    /** 备注信息 */
    private String remark;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}
