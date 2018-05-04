package org.cloudfoundry.samples.music.config.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import io.opentracing.Tracer;

import java.net.MalformedURLException;

@Configuration
@Profile("in-memory")
public class InMemoryConfig {
    @Bean
    public Tracer tracer() throws MalformedURLException
    {
        return TracerProvider.loadTracer();
    }
}
