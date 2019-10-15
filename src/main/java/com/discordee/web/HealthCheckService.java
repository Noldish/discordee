package com.discordee.web;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;

@Health
@ApplicationScoped
public class HealthCheckService implements HealthCheck {

    public HealthCheckResponse call() {
        return HealthCheckResponse.named("healthCheck").withData("foo", "bar").build();

    }
}