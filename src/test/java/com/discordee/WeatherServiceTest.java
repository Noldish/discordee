package com.discordee;


import com.discordee.mock.ForecastClientMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
public class WeatherServiceTest {

    @Inject
    WeatherService weatherService;

    @Inject
    ForecastClientMock forecastClientMock;

    @Test
    public void getWeather() {
        Assertions.assertEquals("Mocked City", weatherService.getWeather().get(0).getCityName());
    }

    @Test
    public void getWeather_cacheTest() {
        for (int i = 0; i < 10; i++) {
            System.out.println(weatherService.getWeather());
        }

        Assertions.assertEquals(1, forecastClientMock.getCounter());
    }
}

