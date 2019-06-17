package com.discordee.listener;

import com.discordee.WeatherService;
import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import reactor.core.publisher.Flux;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CommandListener {

    @Inject
    private WeatherService service;

    public void listen(DiscordClient client) {
        Flux<MessageCreateEvent> messageCreateEvents = client.getEventDispatcher().on(MessageCreateEvent.class);
        Flux<Message> messages = messageCreateEvents.map(MessageCreateEvent::getMessage);
        Flux<Message> pingMessages = messages.filter(msg -> msg.getContent().map(content -> content.equals("!weather")).orElse(false));
        Flux<MessageChannel> textChannels = pingMessages.flatMap(Message::getChannel);
        Flux<Message> sentMessages = textChannels.flatMap(channel -> channel.createMessage(service.getWeatherReport()));
        sentMessages.subscribe();
    }
}
