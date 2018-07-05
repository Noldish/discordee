package com.discordee.config;


import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.infinispan.cdi.embedded.ConfigureCache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

@Singleton
@Startup
public class Config {

    @ConfigureCache("weather-cache")
    @WeatherCache
    @Produces
    public EmbeddedCacheManager specificEmbeddedCacheManager() {
        return new DefaultCacheManager(new ConfigurationBuilder()
                .expiration()
                .lifespan(3600000L)
                .persistence()
                .build());
    }
}
