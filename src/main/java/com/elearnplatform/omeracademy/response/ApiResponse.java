package com.elearnplatform.omeracademy.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T>
{
    private boolean success;
    private String message;
    private T data;

    // Constructors
    public ApiResponse(boolean success, String message) {
        this();
        this.success = success;
        this.message = message;
    }


    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message);
    }
}
