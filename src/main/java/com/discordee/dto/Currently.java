package com.discordee.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

import java.io.Serializable;

@Data
@RegisterForReflection
public class Currently implements Serializable {
    private Integer time;
    private String summary;
    private String icon;
    private Double precipIntensity;
    private Double precipProbability;
    private String precipType;
    private Double temperature;
    private Double apparentTemperature;
    private Double dewPoint;
    private Double humidity;
    private Double pressure;
    private Double windSpeed;
    private Double windGust;
    private Integer windBearing;
    private Double cloudCover;
    private Integer uvIndex;
    private Double visibility;
    private Double ozone;
}