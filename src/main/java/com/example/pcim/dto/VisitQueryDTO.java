package com.example.pcim.dto;

import lombok.Data;

/**
 * 探视登记查询条件DTO
 * 用于分页查询探视登记时传递筛选条件
 */
@Data
public class VisitQueryDTO {
    /** 当前页码，默认为1 */
    private Integer page;

    /** 每页条数，默认为10 */
    private Integer size;

    /** 关键字模糊匹配（监犯编号、姓名、探视人姓名） */
    private String keyword;

    /** 与监犯关系筛选 */
    private String relation;

    /** 探视日期（等于条件，格式：yyyy-MM-dd） */
    private String date;
}
