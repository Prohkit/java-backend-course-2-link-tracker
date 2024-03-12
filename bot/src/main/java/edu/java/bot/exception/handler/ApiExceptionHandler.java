package edu.java.bot.exception.handler;

import edu.java.bot.exception.BadRequestException;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> badRequestException(BadRequestException exception) {
        return ResponseEntity
            .badRequest()
            .body(getBadRequestExceptionResponse(exception));
    }

    private ApiErrorResponse getBadRequestExceptionResponse(Exception exception) {
        return ApiErrorResponse.builder()
            .description("Некорректные параметры запроса")
            .code(String.valueOf(HttpStatus.BAD_REQUEST))
            .exceptionName(exception.getClass().getName())
            .exceptionMessage(exception.getMessage())
            .stacktrace(Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .toList())
            .build();
    }
}
