package com.discordee.config;


import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import org.infinispan.cdi.embedded.ConfigureCache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;

@Singleton
@Startup
public class Config {

    @ConfigureCache("weathercache")
    @WeatherCache
    @Produces
    public Configuration specificEmbeddedCacheManager() {
        return new ConfigurationBuilder()
                .expiration()
                    .lifespan(3600000L)
                .persistence()
                .build();
    }
}
