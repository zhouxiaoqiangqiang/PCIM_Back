package com.example.pcim.dto;

import lombok.Data;

/**
 * 考核评估查询条件DTO
 * 用于分页查询考核评估记录时传递筛选条件
 */
@Data
public class AssessmentQueryDTO {
    /** 当前页码，默认为1 */
    private Integer page;

    /** 每页条数，默认为10 */
    private Integer size;

    /** 关键字模糊匹配（监犯编号、姓名、考核项目） */
    private String keyword;

    /** 考核类型筛选 */
    private String type;

    /** 考核日期筛选 */
    private String date;
}
