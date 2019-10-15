package com.discordee.web;

import com.discordee.entity.WeatherRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRequestRepository extends CrudRepository<WeatherRequest, Long> {

}
