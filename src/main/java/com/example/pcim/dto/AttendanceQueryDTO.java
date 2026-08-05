package com.example.pcim.dto;

import lombok.Data;

@Data
public class AttendanceQueryDTO {
    private String keyword;
    private String cell;
    private String status;
    private String date;
    private Integer page;
    private Integer size;
}
