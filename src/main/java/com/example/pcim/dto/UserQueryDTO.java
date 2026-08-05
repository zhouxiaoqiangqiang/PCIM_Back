package com.example.pcim.dto;

import lombok.Data;

@Data
public class UserQueryDTO {
    private String keyword;
    private Integer status;
    private Integer page;
    private Integer size;
}
