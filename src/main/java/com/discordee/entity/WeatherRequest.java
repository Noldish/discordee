package com.discordee.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NamedQueries({
        @NamedQuery(name = "WeatherRequest.findAll", query = "SELECT e FROM WeatherRequest e")
})
public class WeatherRequest {

    @Id
    private long id;

    private LocalDate requestDate;

    @PrePersist
    private void init() {
        this.requestDate = LocalDate.now();
    }

}
