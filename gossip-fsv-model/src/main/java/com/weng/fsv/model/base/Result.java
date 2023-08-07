package com.weng.fsv.model.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * The type Result.
 *
 * @param <T> the type parameter
 * @Author 翁丞健
 * @Date 2022 /3/31 22:28
 * @Version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private T data;

    private boolean success; // if request is success

    private Integer showType; // error display type： 0 silent; 1 message.warn; 2 message.error; 4 notification; 9 page

    private String host; // onvenient for backend Troubleshooting: host of current access server

    /**
     * Success result.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the result
     */
    public static <T> Result<T> success(T data) {
        return success(StatusCode.COMMON_SUCCESS, data);
    }

    /**
     * Success result.
     *
     * @param <T> the type parameter
     * @return the result
     */
    public static <T> Result<T> success() {
        return success(StatusCode.COMMON_SUCCESS);
    }

    /**
     * Success result.
     *
     * @param <T>  the type parameter
     * @param code the code
     * @param data the data
     * @return the result
     */
    public static <T> Result<T> success(StatusCode code, T data) {
        return new Result<T>(code.getCode(), "", data, true, 0, "127.0.0.1");
    }


    /**
     * Success result.
     *
     * @param <T>  the type parameter
     * @param code the code
     * @return the result
     */
    public static <T> Result<T> success(StatusCode code) {
        return success(code, null);
    }


    /**
     * Failure result.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the result
     */
    public static <T> Result<T> failure(T data) {
        return failure(StatusCode.COMMON_FAIL, data);
    }

    /**
     * Failure result.
     *
     * @param <T> the type parameter
     * @return the result
     */
    public static <T> Result<T> failure() {
        return failure(StatusCode.COMMON_FAIL);
    }

    /**
     * Failure result.
     *
     * @param <T>  the type parameter
     * @param code the code
     * @param data the data
     * @return the result
     */
    public static <T> Result<T> failure(StatusCode code, T data) {
        return new Result<T>(code.getCode(), code.getMessage(), data, false, 0, "127.0.0.1");
    }

    /**
     * Failure result.
     *
     * @param <T>  the type parameter
     * @param code the code
     * @return the result
     */
    public static <T> Result<T> failure(StatusCode code) {
        return failure(code, null);
    }

    /**
     * Failure result.
     *
     * @param <T>     the type parameter
     * @param message the message
     * @return the result
     */
    public static <T> Result<T> failure(String message) {
        return new Result<T>(0, message, null, false, 2, "127.0.0.1");
    }

}
