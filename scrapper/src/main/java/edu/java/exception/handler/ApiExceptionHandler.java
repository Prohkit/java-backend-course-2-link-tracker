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
        return getApiErrorResponse("Ссылка уже добавлена", HttpStatus.BAD_REQUEST, exception);
    }

    private ApiErrorResponse getChatAlreadyRegisteredExceptionResponse(Exception exception) {
        return getApiErrorResponse("Чат уже зарегистрирован", HttpStatus.BAD_REQUEST, exception);
    }

    private ApiErrorResponse getBadRequestExceptionResponse(Exception exception) {
        return getApiErrorResponse("Некорректные параметры запроса", HttpStatus.BAD_REQUEST, exception);
    }

    private ApiErrorResponse getChatDoesNotExistExceptionResponse(Exception exception) {
        return getApiErrorResponse("Чат не найден", HttpStatus.NOT_FOUND, exception);
    }

    private ApiErrorResponse getLinkNotFoundExceptionResponse(Exception exception) {
        return getApiErrorResponse("Ссылка не найдена", HttpStatus.NOT_FOUND, exception);
    }

    private ApiErrorResponse getApiErrorResponse(String description, HttpStatus status, Exception exception) {
        return ApiErrorResponse.builder()
            .description(description)
            .code(String.valueOf(status))
            .exceptionName(exception.getClass().getName())
            .exceptionMessage(exception.getMessage())
            .stacktrace(Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .toList())
            .build();
    }
}
