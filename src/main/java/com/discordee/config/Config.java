package com.discordee.config;

import com.discordee.dto.City;
import org.springframework.context.annotation.Bean;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@org.springframework.context.annotation.Configuration
public class Config {

    @Bean
    List<City> getKotiksCities() {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("city-list.json");
        Jsonb jsonb = JsonbBuilder.create();

        return jsonb.fromJson(inputStream, new ArrayList<City>() {
        }.getClass().getGenericSuperclass());
    }

}
