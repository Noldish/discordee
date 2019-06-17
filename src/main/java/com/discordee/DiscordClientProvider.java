package com.discordee;

import com.discordee.listener.CommandListener;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class DiscordClientProvider {

    private DiscordClient client;

    @Inject @ConfigProperty(name = "bot.token")
    private String token;

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
