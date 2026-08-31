package com.example.pcim.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Prisoner {
    private Long id;
    private String prisonerNo;
    private String name;
    private String gender;
    private Integer age;
    private String idCard;
    private String cell;
    private String crimeType;
    private String sentence;
    /** 风险等级（高风险/中风险/低风险/未评定） */
    private String riskLevel;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date entryDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthDate;
    private String hometown;
    private String socialBackground;
    private String occupation;
    private String photo;
    private String status;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
