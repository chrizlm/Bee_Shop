package com.bee.shop.system;

import org.springframework.http.HttpStatus;

public record CustomResponse<T>(
        HttpStatus status,
        T body,
        String message
) {
    public static <T> CustomResponse<T> of(
            HttpStatus status, T body, String message){
        return new CustomResponse<>(status, body, message);
    }

    public static <T> CustomResponse<T> success(T body){
        return new CustomResponse<>(HttpStatus.OK, body, "Success");
    }
}
