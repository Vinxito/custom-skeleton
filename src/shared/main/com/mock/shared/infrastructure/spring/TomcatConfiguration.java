package com.mock.shared.infrastructure.spring;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfiguration {

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(tomcatConnectorCustomizer());
        return factory;
    }

    private TomcatConnectorCustomizer tomcatConnectorCustomizer() {
        return connector -> {
            connector.setProperty("relaxedQueryChars",
                                  "[,],{,},|");
        };
    }
}
