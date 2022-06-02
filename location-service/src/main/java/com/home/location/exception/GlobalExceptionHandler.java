package com.home.location.exception;

import com.home.location.dto.ClientMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String LOG_MESSAGE = "[{}:{}]";
    private static final String SERVICE_NAME = "location";

    @ExceptionHandler(LocationException.class)
    protected ResponseEntity<ClientMessageDto> handleLocationException(final LocationException exception) {
        log.error(LOG_MESSAGE, exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage(), SERVICE_NAME), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ClientMessageDto> handleException(final Exception exception) {
        log.error(LOG_MESSAGE, exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage(), SERVICE_NAME), HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(final Exception exception,
                                                             final Object body,
                                                             final HttpHeaders headers,
                                                             final HttpStatus status,
                                                             final WebRequest request) {
        log.error(LOG_MESSAGE, exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("The request error", SERVICE_NAME), status);
    }

}
