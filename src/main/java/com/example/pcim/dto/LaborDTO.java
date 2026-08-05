package com.example.pcim.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LaborDTO {
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
    private Date assignDate;
    private String remark;
}
