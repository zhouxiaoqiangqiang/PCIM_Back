package com.example.pcim.dto;

import lombok.Data;

/**
 * 风险评估查询数据传输对象
 */
@Data
public class RiskQueryDTO {
    /** 当前页码 */
    private Integer page;
    /** 每页条数 */
    private Integer size;
    /** 关键字（模糊匹配监犯编号/姓名） */
    private String keyword;
    /** 监室 */
    private String cell;
    /** 风险等级 */
    private String riskLevel;
}
