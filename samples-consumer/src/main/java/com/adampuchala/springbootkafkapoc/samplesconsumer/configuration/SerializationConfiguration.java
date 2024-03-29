package com.adampuchala.springbootkafkapoc.samplesconsumer.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SerializationConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .findAndRegisterModules()
                .enable(SerializationFeature.INDENT_OUTPUT);
    }
}
