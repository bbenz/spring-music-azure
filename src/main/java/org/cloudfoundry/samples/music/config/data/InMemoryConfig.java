package org.cloudfoundry.samples.music.config.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import java.net.MalformedURLException;

@Configuration
@Profile("in-memory")
public class InMemoryConfig {

    @Bean
    public Tracer tracer() throws MalformedURLException

    {
    	// Initialize the OpenTracing Tracer with LightStep's implementation
		Tracer tracer = new com.lightstep.tracer.jre.JRETracer(
         new com.lightstep.tracer.shared.Options.OptionsBuilder()
            .withAccessToken("Access-Token")
            .withComponentName("spring-music")
            .build()
		);
        GlobalTracer.register(tracer);

		return tracer;
    }

}
