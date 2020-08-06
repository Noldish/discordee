package com.client;

import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.SseEventSource;
import java.util.concurrent.Future;

public class RClient {

    public void testAsyncCall() {

        Future<Response> response =
                ClientBuilder.newClient()
                        .target("http://localhost:8081/service-url")
                        .request()
                        .async()
                        .get();
    }


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
