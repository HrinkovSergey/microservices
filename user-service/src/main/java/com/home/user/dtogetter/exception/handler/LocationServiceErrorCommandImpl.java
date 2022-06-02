package com.home.user.dtogetter.exception.handler;

import com.home.user.dtogetter.exception.LocationServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LocationServiceErrorCommandImpl extends AbstractServiceErrorCommand implements ServiceErrorCommand {
    private static final String SERVICE_NAME = "location";

    @Override
    public boolean containsError(Map<String, String> jsonServerResponse, HttpStatus statusCode) {
        return doesContainErrors(jsonServerResponse, statusCode, SERVICE_NAME);
    }

    @Override
    public void handleError(Map<String, String> jsonServerResponse) {
        throw new LocationServiceException(jsonServerResponse.get(MESSAGE_KEY));
    }
}
