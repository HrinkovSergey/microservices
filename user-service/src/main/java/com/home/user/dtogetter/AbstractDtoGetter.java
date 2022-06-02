package com.home.user.dtogetter;

import com.home.user.dtogetter.config.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

abstract class AbstractDtoGetter<T, I> implements DtoGetter<T, I> {
    private Class<T> responseType;
    @Autowired
    private ServerProperties serverProperties;
    private Map<String, String> urlByClassName;
    @Autowired
    private RestTemplate restTemplate;

    @SuppressWarnings("SameParameterValue")
    void setResponseType(Class<T> responseType) {
        this.responseType = responseType;
    }

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
        return restTemplate.getForObject(fullUrl, responseType);
    }

    abstract String getParameters(I args);
}
