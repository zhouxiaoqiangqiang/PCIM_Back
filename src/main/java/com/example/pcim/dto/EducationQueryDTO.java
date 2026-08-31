package com.example.pcim.dto;

import lombok.Data;

/**
 * 教育学习查询条件DTO
 * 用于分页查询教育学习记录时传递筛选条件
 */
@Data
public class EducationQueryDTO {
    /** 当前页码，默认为1 */
    private Integer page;

    /** 每页条数，默认为10 */
    private Integer size;

    /** 关键字模糊匹配（监犯编号、姓名、课程名称） */
    private String keyword;

    /** 学习结果筛选 */
    private String result;
}
