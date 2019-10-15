package com.discordee;

import com.discordee.listener.CommandListener;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class DiscordClientProvider {

    private DiscordClient client;

    @Value("${bot.token}")
    private String token;

    @Autowired
    private CommandListener mentionListener;

    @PostConstruct
    public void init(){
        client = new DiscordClientBuilder(token).build();
        mentionListener.listen(client);
        client.login().subscribe();
    }

    @PreDestroy
    public void destroy() {
        client.logout().block();
    }
}
