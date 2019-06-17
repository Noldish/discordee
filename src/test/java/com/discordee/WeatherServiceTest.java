package com.discordee;

import com.discordee.client.ForecastClient;
import com.discordee.config.WeatherCache;
import com.discordee.dto.City;
import com.discordee.dto.ForecastResponse;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;

@RunWith(CdiRunner.class)
public class WeatherServiceTest {

    @Inject
    WeatherService service;

    @Produces
    List<City> defaultCities = Collections.singletonList(new City());

    @Produces
    @WeatherCache
    Cache<String, List<ForecastResponse>> cache() {
        EmbeddedCacheManager cacheManager = new DefaultCacheManager();
        cacheManager.defineConfiguration("weathercache", new ConfigurationBuilder().build());
        return cacheManager.getCache("weathercache");
    }

    @Produces
    @Mock
    ForecastClient forecastClient;


    @Test
    public void forecastClientCalledOnlyOnce() {
        for (int i = 0; i < 10; i++) {
            service.getWeather();
        }

        Mockito.verify(forecastClient, Mockito.times(1)).getForecastsByCityList(anyList());
    }
}