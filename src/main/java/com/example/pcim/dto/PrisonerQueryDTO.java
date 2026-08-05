package com.example.pcim.dto;

import lombok.Data;

@Data
public class PrisonerQueryDTO {
    private String keyword;
    private String cell;
    private String status;
    private Integer page;
    private Integer size;
}
