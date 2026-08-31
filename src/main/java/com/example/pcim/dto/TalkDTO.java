package com.example.pcim.dto;

import lombok.Data;

import java.util.Date;

/**
 * 谈话记录数据传输对象
 * 用于前后端交互时传递谈话记录的业务数据
 */
@Data
public class TalkDTO {
    /** 主键ID（更新时必传） */
    private Long id;

    /** 监犯编号 */
    private String prisonerNo;

    /** 监犯姓名 */
    private String name;

    /** 所在监室 */
    private String cell;

    /** 谈话日期 */
    private Date talkDate;

    /** 谈话类型 */
    private String type;

    /** 谈话地点 */
    private String location;

    /** 谈话内容 */
    private String content;

    /** 谈话人 */
    private String talker;

    /** 后续跟进措施 */
    private String followUp;

    /** 备注信息 */
    private String remark;
}
