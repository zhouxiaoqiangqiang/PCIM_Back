package com.example.pcim.dto;

import lombok.Data;

import java.util.Date;

/**
 * 刑期档案数据传输对象
 * 用于前端与后端之间的数据交互
 */
@Data
public class SentenceDTO {
    /** 主键ID */
    private Long id;
    /** 入监编号 */
    private String prisonerNo;
    /** 姓名 */
    private String name;
    /** 所在监室 */
    private String cell;
    /** 犯罪类型 */
    private String crimeType;
    /** 入监日期 */
    private Date entryDate;
    /** 原判刑期 */
    private String originalSentence;
    /** 刑期起算日 */
    private Date sentenceStart;
    /** 累计减刑月数 */
    private Integer reducedMonths;
    /** 预计释放日期 */
    private Date releaseDate;
    /** 假释情况 */
    private String parole;
    /** 状态（在押/已释放等） */
    private String status;
    /** 备注 */
    private String remark;
}
