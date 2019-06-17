package com.discordee.dto;

import lombok.Data;

import java.util.List;

@Data
public class City {
    private String name;
    private String longitude;
    private String latitude;
    private List<String> residents = null;
}
