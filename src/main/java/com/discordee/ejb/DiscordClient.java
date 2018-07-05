package com.discordee.ejb;

import com.discordee.dto.City;
import com.discordee.dto.ForecastResponse;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import sx.blah.discord.handle.obj.IEmoji;
import sx.blah.discord.handle.obj.IMessage;

@Stateless
public class DiscordClient {

    @Inject
    private IDiscordClientProvider clientProvider;

    @Inject
    private ForecastClient forecastClient;

    public void addReaction(IMessage message) {
        Optional<IEmoji> emoji = Optional.ofNullable(message.getGuild().getEmojiByName("here"));

        emoji.ifPresent(message::addReaction);
    }

    public void replyAbountWeather(IMessage message) {

        List<City> cities = getKotiksCities();

        List<ForecastResponse> forecasts = forecastClient.getForecastsByCityList(cities);

        Long maxTemp = forecasts.stream().map(f -> f.getCurrently().getTemperature().longValue())
                .max(Comparator.comparingLong(Long::longValue)).get();

        Long minTemp = forecasts.stream().map(f -> f.getCurrently().getTemperature().longValue())
                .min(Comparator.comparingLong(Long::longValue)).get();

        List<String> winners = forecasts.stream()
                .filter(f -> maxTemp.equals(f.getCurrently().getTemperature().longValue()))
                .map(ForecastResponse::getResidents)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        List<String> losers = forecasts.stream()
                .filter(f -> minTemp.equals(f.getCurrently().getTemperature().longValue()))
                .map(ForecastResponse::getResidents)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        String outputMessage = buildWeatherResponse(forecasts, winners, losers);

        message.getChannel().sendMessage(outputMessage);
    }

    private String buildWeatherResponse(List<ForecastResponse> forecastResults, List<String> winners,
            List<String> losers) {

        List<String> formattedForecasts = forecastResults.stream()
                .sorted(Comparator
                        .comparingLong(o -> o.getCurrently().getTemperature().longValue()))
                .map((f) -> {
                    Long temp = f.getCurrently().getTemperature().longValue();
                    String city = f.getCityName();

                    return String.format("%20s %3d°С   \n", city, temp);
                })
                .collect(Collectors.toList());

        StringBuilder outputMessage = new StringBuilder("```css\n");

        formattedForecasts.forEach(outputMessage::append);

        outputMessage.append("\n");
        outputMessage.append("Победители по жизни: ").append(winners).append("\n");
        outputMessage.append("Повезет в следующий раз: ").append(losers);

        outputMessage.append("```");
        return outputMessage.toString();
    }

    private List<City> getKotiksCities() {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("city_list.json");

        Jsonb jsonb = JsonbBuilder.create();

        return jsonb.fromJson(inputStream, new ArrayList<City>() {
        }.getClass().getGenericSuperclass());
    }
}
