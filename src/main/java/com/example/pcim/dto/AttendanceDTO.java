package com.example.pcim.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AttendanceDTO {
    private Long id;
    private Long prisonerId;
    private String prisonerNo;
    private String name;
    private String cell;
    private Date rollCallTime;
    private String status;
    private Date checkInTime;
    private Date checkOutTime;
    private String remark;
}
