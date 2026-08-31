package com.example.pcim.dto;

import lombok.Data;

/**
 * 预警数据传输对象（DTO）
 * 用于前端与后端之间的预警数据交互
 */
@Data
public class WarningDTO {

    /**
     * 主键ID（更新时需要传入）
     */
    private Long id;

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
     * 预警内容描述
     */
    private String content;

    /**
     * 预警来源（如: 视频监控、人工上报、系统检测等）
     */
    private String source;

    /**
     * 处理人姓名
     */
    private String handler;

    /**
     * 处理结果描述
     */
    private String handleResult;

    /**
     * 预警状态（如: 待处理、已处理、已忽略）
     */
    private String status;
}
