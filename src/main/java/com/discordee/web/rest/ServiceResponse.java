package com.discordee.web.rest;

import com.discordee.dto.ForecastResponse;

import java.util.ArrayList;
import java.util.List;

public class ServiceResponse {

    public ServiceResponse(List<ForecastResponse> forecastResponses) {
        this.forecastResponses = forecastResponses;
    }

    public List<ForecastResponse> getForecastResponses() {
        return forecastResponses;
    }

    public void setForecastResponses(List<ForecastResponse> forecastResponses) {
        this.forecastResponses = forecastResponses;
    }

    private List<ForecastResponse> forecastResponses = new ArrayList<>();



}
