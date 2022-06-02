package com.home.user.dtogetter.exception.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
public class ResponseErrorHandlerCommand implements ResponseErrorHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private List<ServiceErrorCommand> serviceErrorCommands;

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

        return (httpResponse.getStatusCode().series() == CLIENT_ERROR
                || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        HttpStatus statusCode = clientHttpResponse.getStatusCode();
        Map<String, String> jsonServerResponse = parseStringJson(clientHttpResponse);
        serviceErrorCommands.stream()
                .filter(serviceError -> serviceError.containsError(jsonServerResponse, statusCode))
                .limit(1)
                .findAny().ifPresent(serviceErrorCommand -> serviceErrorCommand.handleError(jsonServerResponse));
    }

    private Map<String, String> parseStringJson(ClientHttpResponse clientHttpResponse) throws IOException {
        String jsonBody = new String(clientHttpResponse.getBody().readAllBytes(), StandardCharsets.UTF_8);
        return objectMapper.readValue(jsonBody, new TypeReference<>() {
        });
    }
}
