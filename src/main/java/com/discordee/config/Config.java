package com.discordee.config;

import com.discordee.dto.City;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class Config {

    @Produces
    private List<City> getKotiksCities() {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("city-list.json");
        Jsonb jsonb = JsonbBuilder.create();

        return jsonb.fromJson(inputStream, new ArrayList<City>() {
        }.getClass().getGenericSuperclass());
    }

}
