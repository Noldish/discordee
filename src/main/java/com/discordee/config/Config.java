package com.discordee.config;

import com.discordee.dto.City;
import org.infinispan.cdi.embedded.ConfigureCache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
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

    @Produces
    private List<City> getKotiksCities() {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("city-list.json");
        Jsonb jsonb = JsonbBuilder.create();

        return jsonb.fromJson(inputStream, new ArrayList<City>() {
        }.getClass().getGenericSuperclass());
    }

}
