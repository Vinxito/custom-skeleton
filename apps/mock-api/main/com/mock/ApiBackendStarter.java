package com.mock;

import com.mock.shared.domain.Injectable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,
                                                       classes = Injectable.class)},
               value = {"com.mock", "com.mock.shared"})
public class ApiBackendStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApiBackendStarter.class,
                              args);
    }
}

