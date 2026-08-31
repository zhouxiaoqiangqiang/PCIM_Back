package com.example.pcim.dto;

import lombok.Data;

/**
 * 刑期档案查询条件DTO
 * 支持多条件分页查询
 */
@Data
public class SentenceQueryDTO {
    /** 当前页码，默认1 */
    private Integer page;
    /** 每页条数，默认10 */
    private Integer size;
    /** 搜索关键字（匹配入监编号或姓名） */
    private String keyword;
    /** 监室筛选 */
    private String cell;
    /** 状态筛选 */
    private String status;
    /** 是否只返回90天内即将释放的记录（"1"表示是） */
    private String nearRelease;
}
