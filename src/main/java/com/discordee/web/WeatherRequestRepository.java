package com.discordee.web;

import com.discordee.entity.WeatherRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class WeatherRequestRepository {

    @PersistenceContext(unitName = "standalonePu")
    private EntityManager entityManager;


    public void saveRequest(WeatherRequest weatherRequest) {
        entityManager.persist(weatherRequest);
    }


    public List<WeatherRequest> getRequests() {
        return entityManager.createNamedQuery("WeatherRequest.findAll", WeatherRequest.class).getResultList();
    }
}
