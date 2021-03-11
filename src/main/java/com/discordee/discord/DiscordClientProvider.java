package com.discordee.discord;

import com.discordee.weather.WeatherService;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Base64;

@ApplicationScoped
public class DiscordClientProvider {

    @Inject @ConfigProperty(name = "bot.token")
    String encodedForecastKey;

    private String token;

    @PostConstruct
    private void init(){
        byte[] decodedKey = Base64.getDecoder().decode(encodedForecastKey);
        token = new String(decodedKey);
    }

    @Inject
    private WeatherService service;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            if ("!weather".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                channel.createMessage(service.getWeatherReport()).block();
            }
        });
    }
}
