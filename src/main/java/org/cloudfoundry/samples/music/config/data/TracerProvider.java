package org.cloudfoundry.samples.music.config.data;

import java.net.MalformedURLException;

import io.opentracing.Tracer;
import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;


public final class TracerProvider {
    public static final String SERVICE_NAME = "spring-music";

    public static Tracer loadTracer() throws MalformedURLException
    {
        //return loadLightStepTracer();
        return loadJaegerTracer();
    }

    static Tracer loadLightStepTracer() throws MalformedURLException
    {
        // Initialize the OpenTracing Tracer with LightStep's implementation
        return new com.lightstep.tracer.jre.JRETracer(
                new com.lightstep.tracer.shared.Options.OptionsBuilder()
                .withAccessToken("Access-Token")
                .withComponentName(SERVICE_NAME)
                .build()
        );
    }

    static Tracer loadJaegerTracer()
    {
        SamplerConfiguration samplerConfig = new SamplerConfiguration("const", 1);
        ReporterConfiguration reporterConfig = new ReporterConfiguration(true, null, null, null, null);
        Configuration config = new Configuration(SERVICE_NAME, samplerConfig, reporterConfig);
        return config.getTracer();
    }
}
