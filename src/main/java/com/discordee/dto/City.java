package com.discordee.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

import java.util.List;

@Data
@RegisterForReflection
public class City {
    private String name;
    private String longitude;
    private String latitude;
    private List<String> residents = null;
}
