package com.example.pcim.entity;

import lombok.Data;

import java.util.Date;

/**
 * 刑期变动记录实体类
 * 对应数据库表：prison_sentence_change
 * 该模块只允许新增和查询，不允许修改和删除（录错只能新增更正记录）
 */
@Data
public class SentenceChange {
    /** 主键ID */
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
    /** 创建时间 */
    private Date createTime;
}
