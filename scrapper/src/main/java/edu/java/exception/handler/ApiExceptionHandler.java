package edu.java.exception.handler;

import edu.java.exception.BadRequestException;
import edu.java.exception.ChatDoesNotExistException;
import edu.java.exception.LinkNotFoundException;
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

    @ExceptionHandler(ChatDoesNotExistException.class)
    public ResponseEntity<ApiErrorResponse> chatDoesNotExistException(ChatDoesNotExistException exception) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(getChatDoesNotExistExceptionResponse(exception));
    }

    @ExceptionHandler(LinkNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> linkNotFoundException(LinkNotFoundException exception) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(getLinkNotFoundExceptionResponse(exception));
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

    private ApiErrorResponse getChatDoesNotExistExceptionResponse(Exception exception) {
        return ApiErrorResponse.builder()
            .description("Чат не существует")
            .code(String.valueOf(HttpStatus.NOT_FOUND))
            .exceptionName(exception.getClass().getName())
            .exceptionMessage(exception.getMessage())
            .stacktrace(Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .toList())
            .build();
    }

    private ApiErrorResponse getLinkNotFoundExceptionResponse(Exception exception) {
        return ApiErrorResponse.builder()
            .description("Ссылка не найдена")
            .code(String.valueOf(HttpStatus.NOT_FOUND))
            .exceptionName(exception.getClass().getName())
            .exceptionMessage(exception.getMessage())
            .stacktrace(Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .toList())
            .build();
    }
}
