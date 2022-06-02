package com.home.user.dtogetter.exception.handler;

import com.home.user.dtogetter.exception.CompanyServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CompanyServiceErrorCommandImpl extends AbstractServiceErrorCommand implements ServiceErrorCommand {
    private static final String SERVICE_NAME = "company";

    @Override
    public boolean containsError(Map<String, String> jsonServerResponse, HttpStatus statusCode) {
        return doesContainErrors(jsonServerResponse, statusCode, SERVICE_NAME);
    }

    @Override
    public void handleError(Map<String, String> jsonServerResponse) {
        throw new CompanyServiceException(jsonServerResponse.get(MESSAGE_KEY));
    }
}
