package com.discordee.client;

import com.discordee.dto.City;
import com.discordee.dto.ForecastResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tk.plogitech.darksky.forecast.*;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ForecastClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${forecast.key}")
    private String forecastKey;

    public ForecastResponse getForecast(Double longitude, Double latitude) {
        ForecastRequest request = new ForecastRequestBuilder()
                .key(new APIKey(new String(Base64.getDecoder().decode(forecastKey))))
                .language(ForecastRequestBuilder.Language.ru)
                .exclude(ForecastRequestBuilder.Block.daily, ForecastRequestBuilder.Block.hourly,
                        ForecastRequestBuilder.Block.flags)
                .location(new GeoCoordinates(new Longitude(longitude), new Latitude(latitude)))
                .build();

        DarkSkyClient client = new DarkSkyClient();

        try {
            return convertJson(client.forecastJsonString(request));
        } catch (ForecastException e) {
            logger.error("Cannot getForecast", e);
            throw new RuntimeException();
        }
    }

    public ForecastResponse getForecastByCity(City city) {
        ForecastResponse r = getForecast(Double.valueOf(city.getLongitude()),
                Double.valueOf(city.getLatitude()));
        r.setCityName(city.getName());
        r.setResidents(city.getResidents());
        return r;
    }

    public List<ForecastResponse> getForecastsByCityList(List<City> cities) {
        return cities.stream()
                .map(this::getForecastByCity)
                .collect(Collectors.toList());
    }

    private ForecastResponse convertJson(String json) {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.fromJson(json, ForecastResponse.class);
    }


}
