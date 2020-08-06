package com.discordee.weather;

import com.discordee.client.ForecastClient;
import com.discordee.config.WeatherCache;
import com.discordee.dto.City;
import com.discordee.dto.ForecastResponse;
import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class WeatherService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private ForecastClient forecastClient;

    @Inject
    @WeatherCache
    private Cache<String, List<ForecastResponse>> cache;

    @Inject
    private List<City> defaultCities;

    public List<ForecastResponse> getWeather() {
        List<ForecastResponse> cachedValue = cache.get("weather");

        if (cachedValue == null) {
            List<ForecastResponse> forecastsByCityList = forecastClient.getForecastsByCityList(defaultCities);
            cache.put("weather", forecastsByCityList);
            return forecastsByCityList;
        } else {
            return cachedValue;
        }
    }

    public ForecastResponse getWeatherByCityName(String name) {
        City city = defaultCities.stream().filter(c -> name.equals(c.getName())).findAny().orElseThrow(IllegalArgumentException::new);
        return forecastClient.getForecastByCity(city);
    }

    public String getWeatherReport() {
        try {
            return buildWeatherResponse(getWeather());
        } catch (Exception ex){
            logger.error("Exception brother", ex);
            return "404";
        }
    }

    private String buildWeatherResponse(List<ForecastResponse> forecasts) {
        Long maxTemp = forecasts.stream().map(f -> f.getCurrently().getTemperature().longValue())
                .max(Comparator.comparingLong(Long::longValue)).get();

        Long minTemp = forecasts.stream().map(f -> f.getCurrently().getTemperature().longValue())
                .min(Comparator.comparingLong(Long::longValue)).get();

        List<String> winners = forecasts.stream()
                .filter(f -> maxTemp.equals(f.getCurrently().getTemperature().longValue()))
                .map(ForecastResponse::getResidents)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        List<String> losers = forecasts.stream()
                .filter(f -> minTemp.equals(f.getCurrently().getTemperature().longValue()))
                .map(ForecastResponse::getResidents)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return buildWeatherResponse(forecasts, winners, losers);
    }

    private String buildWeatherResponse(List<ForecastResponse> forecastResults, List<String> winners,
                                        List<String> losers) {

        List<String> formattedForecasts = forecastResults.stream()
                .sorted(Comparator.comparingLong(o -> o.getCurrently().getTemperature().longValue()))
                .map((f) -> {
                    Long temp = f.getCurrently().getTemperature().longValue();
                    String city = f.getCityName();

                    return String.format("%20s %3d°С   \n", city, temp);
                })
                .collect(Collectors.toList());

        StringBuilder outputMessage = new StringBuilder("```css\n");

        formattedForecasts.forEach(outputMessage::append);

        outputMessage.append("\n")
                .append("Победители по жизни: ").append(winners).append("\n")
                .append("Повезет в следующий раз: ").append(losers).append("\n")
                .append("```");

        return outputMessage.toString();
    }
}
