package com.example.pcim.dto;

import lombok.Data;

@Data
public class LaborQueryDTO {
    private String keyword;
    private String workType;
    private String status;
    private Integer page;
    private Integer size;
}
