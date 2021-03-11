package com.client;

import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.SseEventSource;

public class RClient {

    @Test
    public void connectToServer() {

        WebTarget client = ClientBuilder.newClient()
                            .target("http://localhost:8180/discordee/api/reactive/weather/sse");

        try (SseEventSource source = SseEventSource.target(client).build()) {
            source.register(System.out::println);
            source.open();

            Thread.sleep(20000);
        } catch (InterruptedException e) {

        }
    }

    @Test
    public void testRxCall() {

        Response response = ClientBuilder.newClient()
                .target("http://localhost:8180/discordee/api/reactive/weather")
                .request()
                .get();

        System.out.println(response);
    }


}
