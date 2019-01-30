package com.discordee.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
public class GlobalProperties {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Properties properties;

    @PostConstruct
    public void init() {
        try {
            InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("/config/config-private.properties");

            properties = new Properties();
            properties.load(inputStream);

        } catch (IOException e) {
            logger.error("Failed to init global properties", e);
        }
    }

    public Optional<String> getPropertyByName(String name) {
        return Optional.ofNullable(properties.getProperty(name));
    }
}
