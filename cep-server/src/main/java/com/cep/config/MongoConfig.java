package com.cep.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
@Profile({"default", "docker-compose"})
@EnableMongoRepositories("com.cep.server.repository")
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${cep.mongodb.hostname}")
    private String mongoHost;

    @Value("${cep.mongodb.port}")
    private String mongoPort;

    @Value("${cep.mongodb.database}")
    private String mongoDB;

    @Override
    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(mongoHost + ":" + mongoPort);
    }

    @Override
    protected String getDatabaseName() {
        return mongoDB;
    }

    @Override
    protected List<String> getMappingBasePackages() {
        return Arrays.asList("com.cep.server.model");
    }
}
