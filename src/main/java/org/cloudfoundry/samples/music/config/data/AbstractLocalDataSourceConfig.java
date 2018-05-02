package org.cloudfoundry.samples.music.config.data;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import io.opentracing.Tracer;

import java.net.MalformedURLException;
import javax.sql.DataSource;

public class AbstractLocalDataSourceConfig {

    protected DataSource createDataSource(String jdbcUrl, String driverClass, String userName, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setDriverClassName(driverClass);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public Tracer tracer() throws MalformedURLException
    {
        return TracerProvider.loadTracer();
    }
}
