package com.discordee.web.rest;

import com.discordee.dto.ForecastResponse;
import com.discordee.weather.WeatherRequest;
import com.discordee.weather.WeatherRequestRepository;
import com.discordee.weather.WeatherService;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/weather")
public class WeatherApi {

    @Inject
    private WeatherService weatherService;

    @Inject
    private WeatherRequestRepository requestRepository;

    @Inject
    private Event<WeatherRequest> weatherRequestEvent;

    @GET
    @Path("/{cityName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "performedChecks", description = "How many primality checks have been performed.")
    @Timed(name = "checksTimer", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
    public Response getWeatherByCityName(@PathParam("cityName") String cityName) {
        ForecastResponse weather = weatherService.getWeatherByCityName(cityName);

        weatherRequestEvent.fireAsync(new WeatherRequest());

        return Response.ok(weather).build();
    }

    @GET
    @Path("/requests")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWeatherRequests() {
        List<WeatherRequest> resultList = requestRepository.getRequests();

        Jsonb jsonb = JsonbBuilder.create();
        String response = jsonb.toJson(resultList);

        return Response.ok(response).build();
    }
}
