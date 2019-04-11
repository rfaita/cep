package com.cep.config;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeoConfig {

    @Value("${cep.maps.apiKey}")
    private String apiKey;

    @Bean
    public GeoApiContext getGeoApiContext() {
        return new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

    }
}
