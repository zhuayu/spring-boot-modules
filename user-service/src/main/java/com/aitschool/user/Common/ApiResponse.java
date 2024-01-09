package com.aitschool.user.Common;

public class ApiResponse<T> {
    private int error_code;
    private String error_message;
    private T data;

    public ApiResponse(int error_code, String error_message, T data) {
        this.error_code = error_code;
        this.error_message = error_message;
        this.data = data;
    }
}
