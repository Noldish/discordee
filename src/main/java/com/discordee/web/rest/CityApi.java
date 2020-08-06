package com.discordee.web.rest;

import com.discordee.dto.City;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("city")
public class CityApi {

    @Inject
    List<City> cityList;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<City> getCityList() {
        return cityList;
    }

}
