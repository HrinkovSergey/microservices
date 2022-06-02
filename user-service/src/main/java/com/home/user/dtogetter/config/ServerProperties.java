package com.home.user.dtogetter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "microservice")
@Setter
@Getter
public class ServerProperties {
    private Map<String, Rout> routes;

    @Getter
    @Setter
    public static class Rout {
        private String className;
        private String baseUrl;
    }
}
