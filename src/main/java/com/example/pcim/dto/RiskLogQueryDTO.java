package com.example.pcim.dto;

import lombok.Data;

/**
 * 风险变更日志查询数据传输对象
 */
@Data
public class RiskLogQueryDTO {
    /** 当前页码 */
    private Integer page;
    /** 每页条数 */
    private Integer size;
    /** 关键字（模糊匹配监犯编号/姓名） */
    private String keyword;
    /** 变更前风险等级 */
    private String oldLevel;
    /** 变更后风险等级 */
    private String newLevel;
}
