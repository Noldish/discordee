package com.discordee.ejb;

import com.discordee.dto.City;
import com.discordee.dto.ForecastResponse;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tk.plogitech.darksky.forecast.APIKey;
import tk.plogitech.darksky.forecast.DarkSkyClient;
import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.ForecastRequest;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder;
import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;

@Stateless
public class ForecastClient {

    private final Logger logger = LogManager.getLogger("ForecastClient");

    @Inject
    private GlobalProperties properties;

    public ForecastResponse getForecast(Double longitude, Double latitude) {
        try {

            String forecastKey = properties.getPropertyByName("forecast.key")
                    .orElseThrow(RuntimeException::new);

            ForecastRequest request = new ForecastRequestBuilder()
                    .key(new APIKey(forecastKey))
                    .language(ForecastRequestBuilder.Language.ru)
                    .exclude(ForecastRequestBuilder.Block.daily, ForecastRequestBuilder.Block.hourly,
                            ForecastRequestBuilder.Block.flags)
                    .location(new GeoCoordinates(new Longitude(longitude), new Latitude(latitude)))
                    .build();

            DarkSkyClient client = new DarkSkyClient();

            return convertJson(client.forecastJsonString(request));
        } catch (ForecastException e) {
            logger.info("Can't get forecast report for longitude: " + longitude
                    + " and latitude: " + latitude);
            return null;
        }
    }

    public List<ForecastResponse> getForecastsByCityList(List<City> cities) {
        return cities.stream()
                .map(c -> {
                    ForecastResponse r = getForecast(Double.valueOf(c.getLongitude()), Double.valueOf(c.getLatitude()));
                    r.setCityName(c.getName());
                    r.setResidents(c.getResidents());
                    return r;
                })
                .collect(Collectors.toList());
    }

    private ForecastResponse convertJson(String json) {
        Jsonb jsonb = JsonbBuilder.create();
        return jsonb.fromJson(json, ForecastResponse.class);
    }


}
