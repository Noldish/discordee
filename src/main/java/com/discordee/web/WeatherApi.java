package com.discordee.web;

import com.discordee.WeatherService;
import com.discordee.dto.ForecastResponse;
import com.discordee.entity.WeatherRequest;

import javax.inject.Inject;
import javax.transaction.Transactional;
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

    @GET
    @Path("/{cityName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getWeatherByCityName(@PathParam("cityName") String cityName){
        ForecastResponse weather = weatherService.getWeatherByCityName(cityName);
        requestRepository.saveRequest(new WeatherRequest());

        return Response.ok(weather).build();
    }

    @GET
    @Path("/requests")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWeatherRequests(){
        List<WeatherRequest> resultList = requestRepository.getRequests();

        return Response.ok(resultList).build();
    }
}
