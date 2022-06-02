package com.home.user.dtogetter.exception.handler;

import org.springframework.http.HttpStatus;

import java.util.Map;

public interface ServiceErrorCommand {

    boolean containsError(Map<String, String> jsonServerResponse, HttpStatus statusCode);

    void handleError(Map<String, String> jsonServerResponse);
}
