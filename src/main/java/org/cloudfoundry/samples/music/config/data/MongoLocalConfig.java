package org.cloudfoundry.samples.music.config.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
@Profile("mongodb-local")
public class MongoLocalConfig {
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    public MongoDbFactory mongoDbFactory() {
        MongoClient mc;

        mc = new MongoClient(new MongoClientURI(mongoUri));

        return new SimpleMongoDbFactory(mc, "music");
    }

} 
 
