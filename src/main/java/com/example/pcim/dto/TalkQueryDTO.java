package com.example.pcim.dto;

import lombok.Data;

/**
 * 谈话记录查询条件DTO
 * 用于分页查询谈话记录时传递筛选条件
 */
@Data
public class TalkQueryDTO {
    /** 当前页码，默认为1 */
    private Integer page;

    /** 每页条数，默认为10 */
    private Integer size;

    /** 关键字模糊匹配（监犯编号、姓名、谈话内容） */
    private String keyword;

    /** 谈话类型筛选 */
    private String type;
}
