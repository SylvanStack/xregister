package com.yuanstack.xregister.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * wrap exception response.
 *
 * @author Sylvan
 * @date 2024/05/15  0:04
 */
@AllArgsConstructor
@Data
public class ExceptionResponse {
    private HttpStatus httpStatus;
    private String message;
}
