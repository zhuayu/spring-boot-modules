package com.aitschool.common.response;

import lombok.Data;

@Data
public class CommonResponse<T> {

    private int error_code = 0;
    private String message;
    private T data;

    public CommonResponse() {
    }

    public CommonResponse(int error_code, String message, T data) {
        this.error_code = error_code;
        this.message = message;
        this.data = data;
    }

    public CommonResponse(T data) {
        this.data = data;
    }
}
