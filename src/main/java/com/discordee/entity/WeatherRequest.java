package com.discordee.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NamedQueries({
        @NamedQuery(name = "WeatherRequest.findAll", query = "SELECT e FROM WeatherRequest e")
})
public class WeatherRequest {

    @Id
    @GeneratedValue
    private long id;

    private LocalDate requestDate;

    @PrePersist
    private void init() {
        this.requestDate = LocalDate.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }
}
