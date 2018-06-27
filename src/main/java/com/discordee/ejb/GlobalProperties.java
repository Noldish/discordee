package com.discordee.ejb;

import java.io.IOException;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import java.io.InputStream;
import java.util.Properties;
import javax.ejb.Startup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Singleton
@Startup
public class GlobalProperties {

    private static final Logger logger = LogManager.getLogger("GlobalProperties");

    private Properties properties;

    @PostConstruct
    public void init() {
        try {
            InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("config_private.properties");

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
