package com.example.pcim.entity;

import lombok.Data;

import java.util.Date;

/**
 * 谈话记录实体类
 * 对应数据库表 prison_talk
 */
@Data
public class Talk {
    /** 主键ID */
    private Long id;

    /** 监犯编号 */
    private String prisonerNo;

    /** 监犯姓名 */
    private String name;

    /** 所在监室 */
    private String cell;

    /** 谈话日期 */
    private Date talkDate;

    /** 谈话类型（如：日常谈话、心理疏导、政策教育等） */
    private String type;

    /** 谈话地点 */
    private String location;

    /** 谈话内容 */
    private String content;

    /** 谈话人（经办人） */
    private String talker;

    /** 后续跟进措施 */
    private String followUp;

    /** 备注信息 */
    private String remark;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}
