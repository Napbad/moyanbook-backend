package com.moyanshushe.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class Result {
    private String message; // message
    private Object data; // Result
    private Short status; // 1:success others: failed

    private Result(String message, Object data, Short status) {
        this.message = message;
        this.data = data;
        this.status = status;

    }

    public static Result success(String message, Object data) {
        return new Result(message, data, (short) 1);
    }

    public static Result success(String message) {
        return new Result(message, null, (short) 1);
    }

    public static Result success(Object data) {
        return new Result("", data, (short) 1);
    }

    public static Result success() {
        return new Result("", null, (short) 1);
    }

    public static Result error(String message) {
        return new Result(message, null, (short) 0);
    }
}
