package com.example.pcim.dto;

import lombok.Data;

import java.util.Date;

/**
 * 刑期变动记录数据传输对象
 * 用于前端与后端之间的数据交互
 */
@Data
public class SentenceChangeDTO {
    /** 主键ID（新增时不需要传入） */
    private Long id;
    /** 入监编号 */
    private String prisonerNo;
    /** 姓名 */
    private String name;
    /** 变动类型（如：减刑、假释、加刑等） */
    private String changeType;
    /** 原值 */
    private String oldValue;
    /** 新值 */
    private String newValue;
    /** 变动日期 */
    private Date changeDate;
    /** 操作人 */
    private String operator;
    /** 变动原因 */
    private String reason;
    /** 备注 */
    private String remark;
}
