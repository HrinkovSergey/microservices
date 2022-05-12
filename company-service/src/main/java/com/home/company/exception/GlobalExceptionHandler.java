package com.home.company.exception;

import com.home.company.dto.ClientMessageDto;
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
    @ExceptionHandler(CompanyException.class)
    protected ResponseEntity<ClientMessageDto> handleLocationException(final CompanyException exception) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(final Exception exception,
            final Object body,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {
        log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
        return new ResponseEntity<>(new ClientMessageDto("The request error"), status);
    }
}
