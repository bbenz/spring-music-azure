package org.cloudfoundry.samples.music;

import org.cloudfoundry.samples.music.config.SpringApplicationContextInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    
    /*added to handle war file building for wildfly
   
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    end added to handle war file building for wildfly */

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).
                initializers(new SpringApplicationContextInitializer())
                .application()
                .run(args);
    }



}

