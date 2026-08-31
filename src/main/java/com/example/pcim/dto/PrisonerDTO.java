package com.example.pcim.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PrisonerDTO {
    private Long id;
    private String prisonerNo;
    private String name;
    private String gender;
    private Integer age;
    private String idCard;
    private String cell;
    private String crimeType;
    private String sentence;
    private Date entryDate;
    private Date birthDate;
    private String hometown;
    private String socialBackground;
    private String occupation;
    private String photo;
    private String status;
    /** 风险等级（高风险/中风险/低风险/未评定） */
    private String riskLevel;
    private String remark;
}
