package com.discordee.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ForecastResponse implements Serializable {
    private Double latitude;
    private Double longitude;
    private String timezone;
    private Currently currently;
    private Integer offset;
    private String cityName;
    private List<String> residents;
}