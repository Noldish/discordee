package com.discordee.config;

import java.util.Optional;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class GlobalProperties {

    private Properties properties;

    @PostConstruct
    public void init() {
        properties = new Properties();

        String botToken = System.getenv("bot.token");
        properties.setProperty("bot.token", botToken);

        String forecastToken = System.getenv("forecast.key");
        properties.setProperty("forecast.key", forecastToken);
    }

    public Optional<String> getPropertyByName(String name) {
        return Optional.ofNullable(properties.getProperty(name));
    }
}
