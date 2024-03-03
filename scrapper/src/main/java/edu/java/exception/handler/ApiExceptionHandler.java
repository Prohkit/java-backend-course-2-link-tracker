package edu.java.exception.handler;

import edu.java.exception.BadRequestException;
import edu.java.exception.ChatAlreadyRegisteredException;
import edu.java.exception.ChatDoesNotExistException;
import edu.java.exception.LinkHasAlreadyBeenAddedException;
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

    @ExceptionHandler(ChatAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> chatAlreadyRegisteredException(ChatAlreadyRegisteredException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(getChatAlreadyRegisteredExceptionResponse(exception));
    }

    @ExceptionHandler(LinkHasAlreadyBeenAddedException.class)
    public ResponseEntity<ApiErrorResponse> linkHasAlreadyBeenAddedException(
        LinkHasAlreadyBeenAddedException exception
    ) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(getLinkHasAlreadyBeenAddedExceptionResponse(exception));
    }

    private ApiErrorResponse getLinkHasAlreadyBeenAddedExceptionResponse(Exception exception) {
        ApiErrorResponse errorResponse = getApiErrorResponse(exception);
        errorResponse.setDescription("Ссылка уже добавлена");
        errorResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
        return errorResponse;
    }

    private ApiErrorResponse getChatAlreadyRegisteredExceptionResponse(Exception exception) {
        ApiErrorResponse errorResponse = getApiErrorResponse(exception);
        errorResponse.setDescription("Чат уже зарегистрирован");
        errorResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
        return errorResponse;
    }

    private ApiErrorResponse getBadRequestExceptionResponse(Exception exception) {
        ApiErrorResponse errorResponse = getApiErrorResponse(exception);
        errorResponse.setDescription("Некорректные параметры запроса");
        errorResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
        return errorResponse;
    }

    private ApiErrorResponse getChatDoesNotExistExceptionResponse(Exception exception) {
        ApiErrorResponse errorResponse = getApiErrorResponse(exception);
        errorResponse.setDescription("Чат не найден");
        errorResponse.setCode(String.valueOf(HttpStatus.NOT_FOUND));
        return errorResponse;
    }

    private ApiErrorResponse getLinkNotFoundExceptionResponse(Exception exception) {
        ApiErrorResponse errorResponse = getApiErrorResponse(exception);
        errorResponse.setDescription("Ссылка не найдена");
        errorResponse.setCode(String.valueOf(HttpStatus.NOT_FOUND));
        return errorResponse;
    }

    private ApiErrorResponse getApiErrorResponse(Exception exception) {
        return ApiErrorResponse.builder()
            .exceptionName(exception.getClass().getName())
            .exceptionMessage(exception.getMessage())
            .stacktrace(Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .toList())
            .build();
    }
}
