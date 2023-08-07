package com.weng.fsv.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum Status code.
 *
 * @Author 翁丞健
 * @Date 2022 /3/31 22:32
 * @Version 1.0.0
 */
@Getter
@AllArgsConstructor
public enum StatusCode {
    /**
     * Common success status code.
     */
    COMMON_SUCCESS(200, "成功调用"),
    /**
     * Common fail status code.
     */
    COMMON_FAIL(0, "失败调用"),

    LOGOUT(207, "登出"),

    REGISTER_SUCCESS(201, "注册成功"),

    LOGIN_SUCCESS(202, "登录成功"),


    REDIRECTING(302, "重定向请求"),

    /**
     * Unkown status code.
     */
    UNKOWN(500, "系统内部错误,请联系管理员"),
    /**
     * Path not found status code.
     */
    PATH_NOT_FOUND(404, "路径不存在，请检查路径"),
    /**
     * Access denied status code.
     */
    ACCESS_DENIED(403, "没有权限，请联系管理员"),
    /**
     * Duplicate key status code.
     */
    DUPLICATE_KEY(501, "数据库中已存在该记录"),
    /**
     * Token generator error status code.
     */
    TOKEN_GENERATOR_ERROR(502, "token生成失败"),
    /**
     * No uuid status code.
     */
    NO_UUID(503, "uuid为空"),
    /**
     * Sql illegal status code.
     */
    SQL_ILLEGAL(504, "sql非法"),

    SYSTEM_BUSY(505, "系统繁忙,请稍后再试"),

    /**
     * Not login status code.
     */
    NOT_LOGIN(401, "用户未登陆"),
    /**
     * User not found status code.
     */
    USER_NOT_FOUND(505, "用户名不存在"),
    /**
     * User freeze status code.
     */
    USER_FREEZE(506, "用户已被冻结"),
    /**
     * User password fail status code.
     */
    USER_PASSWORD_FAIL(507, "用户名或密码错误"),
    /**
     * Login fail status code.
     */
    LOGIN_FAIL(508, "登录失败"),


    CREDENTIALS_EXPIRED(509, "凭证过期"),

    ACCOUNT_LOCKED(510, "账号被锁定"),
    ACCOUNT_DISABLED(511, "账号不可用");
    /**
     * The Code.
     */
    final Integer code;

    /**
     * The Message.
     */
    final String message;

    public static StatusCode from(int code) {
        for (StatusCode value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        return StatusCode.COMMON_SUCCESS;
    }
}
