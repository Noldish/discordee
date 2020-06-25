package com.discordee;

import com.discordee.listener.CommandListener;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Base64;

@ApplicationScoped
public class DiscordClientProvider {

    private DiscordClient client;

    @Inject @ConfigProperty(name = "bot.token")
    String encodedForecastKey;

    private String token;

    @PostConstruct
    private void init(){
        byte[] decodedKey = Base64.getDecoder().decode(encodedForecastKey);
        token = new String(decodedKey);
    }

    @Inject
    private CommandListener mentionListener;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        client = new DiscordClientBuilder(token).build();
        mentionListener.listen(client);
        client.login().subscribe();
    }

    @PreDestroy
    public void destroy() {
        client.logout().block();
    }
}
