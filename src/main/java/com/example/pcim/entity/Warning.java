package com.example.pcim.entity;

import lombok.Data;

import java.util.Date;

/**
 * 预警信息实体类
 * 对应数据库表: prison_warning
 */
@Data
public class Warning {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 预警编号（自动生成，格式: YJ + 年月日 + 4位随机数）
     */
    private String warningNo;

    /**
     * 犯人编号
     */
    private String prisonerNo;

    /**
     * 犯人姓名
     */
    private String name;

    /**
     * 所在监室
     */
    private String cell;

    /**
     * 预警类型（如: 行为异常、情绪波动、健康异常等）
     */
    private String type;

    /**
     * 预警级别（如: 低、中、高）
     */
    private String level;

    /**
     * 预警发生时间
     */
    private Date warningTime;

    /**
     * 预警内容描述
     */
    private String content;

    /**
     * 预警状态（如: 待处理、已处理、已忽略）
     */
    private String status;

    /**
     * 预警来源（如: 视频监控、人工上报、系统检测等）
     */
    private String source;

    /**
     * 处理人姓名
     */
    private String handler;

    /**
     * 处理时间
     */
    private Date handleTime;

    /**
     * 处理结果描述
     */
    private String handleResult;

    /**
     * 是否已读（0: 未读, 1: 已读）
     */
    private Integer isRead;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
