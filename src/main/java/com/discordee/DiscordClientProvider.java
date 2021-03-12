package com.discordee;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;

@Component
public class DiscordClientProvider {

    @Value("${bot.token}")
    private String token;

    @Autowired
    private WeatherService service;

    @PostConstruct
    public void init(){

        byte[] decodedKey = Base64.getDecoder().decode(token);
        String encodedToken = new String(decodedKey);

        final DiscordClient client = DiscordClient.create(encodedToken);
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
