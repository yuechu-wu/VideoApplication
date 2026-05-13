package com.wuyue.video.common;

public record Result<T>(Integer code, String msg, T data) {
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }
    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }
}