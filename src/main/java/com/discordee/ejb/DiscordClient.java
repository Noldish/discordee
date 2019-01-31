package com.discordee.ejb;

import com.discordee.dto.City;
import com.discordee.dto.ForecastResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IEmoji;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;

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

    public void replyAboutWeather(IMessage message) {

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

        EmbedObject embedObject = buildWeatherResponseWithLogo(forecasts, winners, losers);

        message.getChannel().sendMessage(embedObject);
    }
    
    private EmbedObject buildWeatherResponseWithLogo(List<ForecastResponse> forecastResults, List<String> winners,
            List<String> losers) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.withImage("https://media.discordapp.net/attachments/235804189240852480/540483625947234305/orda111.png?width=400&height=48");

        builder.appendField("Погодка", buildWeatherResponse(forecastResults, winners, losers), false);

        return builder.build();
    }

    private String buildWeatherResponse(List<ForecastResponse> forecastResults, List<String> winners,
            List<String> losers) {

        List<String> formattedForecasts = forecastResults.stream()
                .sorted(Comparator.comparingLong(o -> o.getCurrently().getTemperature().longValue()))
                .map((f) -> {
                    Long temp = f.getCurrently().getTemperature().longValue();
                    String city = f.getCityName();

                    return String.format("%20s %3d°С   \n", city, temp);
                })
                .collect(Collectors.toList());

        StringBuilder outputMessage = new StringBuilder("```css\n");

        formattedForecasts.forEach(outputMessage::append);

        outputMessage.append("\n")
                .append("Победители по жизни: ").append(winners).append("\n")
                .append("Повезет в следующий раз: ").append(losers).append("\n")
                .append("```");

        return outputMessage.toString();
    }

    private List<City> getKotiksCities() {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("city-list.json");

        Jsonb jsonb = JsonbBuilder.create();

        return jsonb.fromJson(inputStream, new ArrayList<City>() {
        }.getClass().getGenericSuperclass());
    }

}
