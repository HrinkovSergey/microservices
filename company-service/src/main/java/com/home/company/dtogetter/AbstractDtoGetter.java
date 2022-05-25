package com.home.company.dtogetter;

import com.home.company.dtogetter.config.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

abstract class AbstractDtoGetter<T, I> implements DtoGetter<T, I> {
    void setResponseType(Class<T> responseType) {
        this.responseType = responseType;
    }

    private Class<T> responseType;
    @Autowired
    private ServerProperties serverProperties;

    private Map<String, String> urlByClassName;

    @Autowired
    private RestTemplate restTemplate;

    @SuppressWarnings("rawtypes")
    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @PostConstruct
    private void init() {
        urlByClassName = new HashMap<>();
        if (serverProperties.getRoutes() != null) {
            serverProperties.getRoutes().values().forEach(
                    route -> urlByClassName.put(route.getClassName(), route.getBaseUrl()));
        }
    }

    @Override
    public T getDtoFromExternalResource(I arg) {
        String baseUrl = urlByClassName.get(responseType.getSimpleName());
        String fullUrl = baseUrl + "/" + getParameters(arg);
        return circuitBreakerFactory.create("circuitbreaker")
                .run(() -> restTemplate.getForObject(fullUrl, responseType));
    }

    abstract String getParameters(I args);
}
