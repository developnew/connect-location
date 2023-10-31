package com.hannew.connect.location.domain.enumeration;

import lombok.Getter;

@Getter
public enum Result {
    //region common
    OK(0, "정상처리"),
    FAIL(-1, "실패"),
    INCORRECT_REQUEST(-2,"Incorrect Request")
    ;

    private final int code;
    private final String message;

    Result(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static Result valueOf(int code) {
        Result result = resolve(code);
        if (result == null) {
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        }
        return result;
    }

    public static Result resolve(int code) {
        for (Result result : values()) {
            if (result.code == code) {
                return result;
            }
        }
        return null;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}