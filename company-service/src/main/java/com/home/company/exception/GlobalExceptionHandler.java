package com.home.company.exception;

import com.home.company.dto.ClientMessageDto;
import com.home.company.dtogetter.exception.LocationServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
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
    private static final String ERROR_FORMAT = "[{}:{}]";
    private static final String SERVICE_NAME = "company";

    @ExceptionHandler(CompanyException.class)
    protected ResponseEntity<ClientMessageDto> handleLocationException(final CompanyException exception) {
        log.error(ERROR_FORMAT, exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage(), SERVICE_NAME), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LocationServiceException.class)
    protected ResponseEntity<ClientMessageDto> handleLocationServiceException(
            final LocationServiceException exception) {
        log.error(ERROR_FORMAT, exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage(), SERVICE_NAME), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoFallbackAvailableException.class)
    protected ResponseEntity<ClientMessageDto> handleCircuitBreakerException(
            final NoFallbackAvailableException exception) {
        log.error(ERROR_FORMAT, exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("Location server isn't available", SERVICE_NAME),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected ResponseEntity<Object> handleExceptionInternal(final Exception exception,
            final Object body,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {
        log.error(ERROR_FORMAT, exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("The request error", SERVICE_NAME), status);
    }
}
