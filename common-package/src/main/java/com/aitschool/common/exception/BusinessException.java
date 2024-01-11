package com.aitschool.common.exception;

public class BusinessException extends RuntimeException {

    private String  e;

    public BusinessException(String e) {
        this.e = e;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    /**
     * 不写入堆栈信息，提高性能
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
