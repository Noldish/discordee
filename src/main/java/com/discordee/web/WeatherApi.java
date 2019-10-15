package com.discordee.web;

import com.discordee.WeatherService;
import com.discordee.dto.ForecastResponse;
import com.discordee.entity.WeatherRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class WeatherApi {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherRequestRepository requestRepository;

    @GetMapping("weather")
    public List<ForecastResponse> getWeather() {
        return weatherService.getWeather();
    }

    @GetMapping("weather/{cityName}")
    @Transactional
    public ForecastResponse getWeatherByCityName(@PathVariable("cityName") String cityName) {
        ForecastResponse weather = weatherService.getWeatherByCityName(cityName);
        requestRepository.save(new WeatherRequest());

        return weather;
    }

    @GetMapping("weather/requests")
    public List<WeatherRequest> getWeatherRequests() {
        return (List<WeatherRequest>) requestRepository.findAll();
    }
}
