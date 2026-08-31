package com.example.pcim.dto;

import lombok.Data;

/**
 * 预警查询条件数据传输对象（QueryDTO）
 * 用于封装分页查询和筛选条件
 */
@Data
public class WarningQueryDTO {

    /**
     * 当前页码（从1开始，默认为1）
     */
    private Integer page;

    /**
     * 每页条数（默认为10）
     */
    private Integer size;

    /**
     * 关键词搜索（模糊匹配: 预警编号、犯人编号、犯人姓名）
     */
    private String keyword;

    /**
     * 预警类型筛选
     */
    private String type;

    /**
     * 预警级别筛选
     */
    private String level;

    /**
     * 预警状态筛选
     */
    private String status;
}
