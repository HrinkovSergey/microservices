package com.home.company.dtogetter.exception;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

public class LocationResponseErrorHandler implements ResponseErrorHandler {

    private static final String MESSAGE_KEY = "message";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return (
                httpResponse.getStatusCode().series() == CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        if (clientHttpResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
            String jsonBody = new String(clientHttpResponse.getBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> objectByString = objectMapper.readValue(jsonBody, new TypeReference<>() {
            });
            String message = objectByString.get(MESSAGE_KEY);
            throw new LocationServiceException(message);
        }
    }
}
