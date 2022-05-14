package com.home.company;

import com.home.logger.LogMethodBeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(LogMethodBeanPostProcessor.class)
public class CompanyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyServiceApplication.class, args);
    }
}
