package com.example.pcim.dto;

import lombok.Data;

@Data
public class Response<T> {
    private Integer code;
    private String message;
    private T data;

    private Response(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(200, "操作成功！", data);
    }

    public static <T> Response<T> success(String message, T data) {
        return new Response<>(200, message, data);
    }

    public static <T> Response<T> success() {
        return new Response<>(200, "操作成功！", null);
    }

    public static <T> Response<T> error(Integer code, String message) {
        return new Response<>(code, message, null);
    }

    public static <T> Response<T> error(String message) {
        return new Response<>(500, message, null);
    }

    public static <T> Response<T> unauthorized() {
        return new Response<>(401, "未授权", null);
    }
}
