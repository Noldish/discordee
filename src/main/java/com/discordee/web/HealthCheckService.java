package com.discordee.web;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class HealthCheckService implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().status("We cool").build();
    }
}