package com.home.user.dtogetter.exception.handler;

import org.springframework.http.HttpStatus;

import java.util.Map;

public abstract class AbstractServiceErrorCommand implements ServiceErrorCommand {
    static final String MESSAGE_KEY = "message";
    static final String SERVICE_KEY = "service";

    boolean doesContainErrors(Map<String, String> jsonServerResponse, HttpStatus statusCode, String serviceName) {
        if (statusCode == HttpStatus.BAD_REQUEST) {
            String service = jsonServerResponse.get(SERVICE_KEY);
            return service.equals(serviceName);
        }
        return false;
    }
}
