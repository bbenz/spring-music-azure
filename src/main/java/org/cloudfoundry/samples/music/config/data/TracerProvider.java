package org.cloudfoundry.samples.music.config.data;

import io.opentracing.Tracer;
import java.net.MalformedURLException;

public final class TracerProvider {
    public static Tracer loadTracer() throws MalformedURLException
    {
        // Initialize the OpenTracing Tracer with LightStep's implementation
        return new com.lightstep.tracer.jre.JRETracer(
                new com.lightstep.tracer.shared.Options.OptionsBuilder()
                .withAccessToken("Access-Token")
                .withComponentName("spring-music")
                .build()
        );
    }
}
