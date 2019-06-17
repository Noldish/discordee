package com.discordee.web;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;

@Health
@ApplicationScoped
public class HealthCheckService implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("health")
                .up()
                .withData("Author", "Ivo Woltring")
                .withData("Website", "https://www.ivonet.nl")
                .build();
    }
}