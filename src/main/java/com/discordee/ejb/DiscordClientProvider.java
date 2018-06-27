package com.discordee.ejb;

import com.discordee.ejb.supply.AnnotationListener;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

@Singleton
@Startup
@DependsOn("GlobalProperties")
public class DiscordClientProvider {

    private final Logger logger = LogManager.getLogger("DiscordClientProvider");
    private IDiscordClient client = null;

    @Inject
    private GlobalProperties properties;

    @PostConstruct
    public void init() {

        Optional<String> token = properties.getPropertyByName("bot.token");

        if (token.isPresent()) {
            client = createClient(token.get(), true);
        } else {
            logger.error("Token not found in config. DiscordClient can't be init");
            return;
        }

        assert client != null;
        initEventDispatchers();
    }

    public IDiscordClient getClient() {
        return client;
    }

    private IDiscordClient createClient(String token, boolean login) {
        try {
            ClientBuilder clientBuilder = new ClientBuilder();
            clientBuilder.withToken(token);
            return login ? clientBuilder.login() : clientBuilder.build();
        } catch (DiscordException e) {
            logger.error("Failed to init DiscordClient", e);
            return null;
        }
    }

    private void initEventDispatchers(){
        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(new AnnotationListener());
    }
}
