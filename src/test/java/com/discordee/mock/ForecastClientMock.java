package com.discordee.mock;

import com.discordee.client.ForecastClient;
import com.discordee.dto.City;
import com.discordee.dto.ForecastResponse;
import io.quarkus.test.Mock;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.List;

@Mock
@ApplicationScoped
public class ForecastClientMock extends ForecastClient {

    private static int counter = 0;

    @Override
    public List<ForecastResponse> getForecastsByCityList(List<City> cities) {
        counter = counter + 1;

        ForecastResponse forecastResponse = new ForecastResponse();
        forecastResponse.setCityName("Mocked City");

        return Collections.singletonList(forecastResponse);
    }

    public int getCounter() {
        return counter;
    }
}
