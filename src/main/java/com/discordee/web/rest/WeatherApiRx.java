package com.discordee.web.rest;

import com.discordee.dto.City;
import com.discordee.dto.ForecastResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Collectors;

@Path("/reactive/weather")
public class WeatherApiRx {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getWeather(@Suspended final AsyncResponse async) {
        CompletionStage<List<City>> citiesCS = ClientBuilder.newClient()
                .target("http://localhost:8180/discordee/api/city")
                .request()
                .rx()
                .get(new GenericType<List<City>>() {
                });

        Function<City, ForecastResponse> getForecast = (city) -> ClientBuilder.newClient()
                .target("http://localhost:8180/discordee/api/weather/{cityName}")
                .resolveTemplate("cityName", city.getName())
                .request()
                .get(ForecastResponse.class);

        CompletionStage<List<ForecastResponse>> listCompletionStage = citiesCS.thenApply(
                (cities -> cities.stream().map(getForecast).collect(Collectors.toList())));

        listCompletionStage.whenComplete((list, th) -> async.resume(list));

    }

    @GET
    @Path("/sse")
    @Produces("text/event-stream")
    public void getStockPrices(@Context SseEventSink sseEventSink, @Context Sse sse) {
        try (SseEventSink sink = sseEventSink) {
            sink.send(sse.newEvent("data"));

            Thread.sleep(5000);

            sink.send(sse.newEvent("MyEventName", "more data"));

            Thread.sleep(5000);

            OutboundSseEvent event = sse.newEventBuilder().
                    id("EventId").
                    name("EventName").
                    data("Data").
                    reconnectDelay(10000).
                    comment("Anything i wanna comment here!").
                    build();

            sink.send(event);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
