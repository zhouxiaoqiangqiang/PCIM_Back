package com.example.pcim.dto;

import lombok.Data;

/**
 * 刑期变动记录查询条件DTO
 * 支持关键字和入监编号查询
 */
@Data
public class SentenceChangeQueryDTO {
    /** 当前页码，默认1 */
    private Integer page;
    /** 每页条数，默认10 */
    private Integer size;
    /** 搜索关键字（匹配姓名或入监编号） */
    private String keyword;
    /** 入监编号精确筛选 */
    private String prisonerNo;
}
