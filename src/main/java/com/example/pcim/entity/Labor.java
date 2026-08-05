package com.example.pcim.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Labor {
    private Long id;
    private Long prisonerId;
    private String prisonerNo;
    private String name;
    private String cell;
    private String workType;
    private String workPlace;
    private Integer workHours;
    private Integer workScore;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date assignDate;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
