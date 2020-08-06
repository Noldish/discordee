package com.discordee.weather;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class WeatherRequestObserver {

    @Inject
    private WeatherRequestRepository requestRepository;

    public void observerWeatherRequestEvent(@ObservesAsync WeatherRequest request) {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        requestRepository.saveRequest(request);
    }

}
