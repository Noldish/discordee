package com.discordee.weather;

import com.discordee.weather.WeatherRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class WeatherRequestRepository {

    @PersistenceContext(unitName = "standalonePu")
    private EntityManager entityManager;

    @Transactional
    public void saveRequest(WeatherRequest weatherRequest) {
        entityManager.persist(weatherRequest);
    }


    public List<WeatherRequest> getRequests() {
        return entityManager.createNamedQuery("WeatherRequest.findAll", WeatherRequest.class).getResultList();
    }
}
