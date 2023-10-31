package com.hannew.connect.location.adapter.out;

import com.hannew.connect.location.domain.enumeration.Result;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEntity<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ResponseEntity<T> success(T data) {
        return ResponseEntity.<T>builder()
                .code(Result.OK.getCode())
                .message(Result.OK.getMessage())
                .data(data)
                .build();
    }

    public static <T> ResponseEntity<T> success() {
        return ResponseEntity.<T>builder()
                .code(Result.OK.getCode())
                .message(Result.OK.getMessage())
                .build();
    }

    public static <T> ResponseEntity<T> fail(Result result) {
        return ResponseEntity.<T>builder()
                .code(result.getCode())
                .message(result.getMessage())
                .build();
    }

    @Builder
    public ResponseEntity(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
