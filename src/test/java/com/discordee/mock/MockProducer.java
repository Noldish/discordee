package com.discordee.mock;

import com.discordee.dto.City;
import io.quarkus.test.Mock;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class MockProducer {

    @Mock
    @Produces
    List<City> produceMockedList() {
       return Collections.emptyList();
    }

}
