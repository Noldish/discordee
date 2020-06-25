package com.discordee.web;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class HealthCheckService implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("health")
                .up()
                .withData("Status", "ok")
                .build();
    }
}