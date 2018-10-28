package com.discordee.ejb;

import com.discordee.config.GlobalProperties;
import com.discordee.ejb.listener.MentionListener;
import com.discordee.ejb.listener.MessageListener;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

@Singleton
@Startup
@DependsOn("GlobalProperties")
public class IDiscordClientProvider {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private IDiscordClient client = null;

    @Inject
    private GlobalProperties properties;

    @Inject
    private MessageListener messageListener;

    @Inject
    private MentionListener mentionListener;

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
        registerEventDispatchers();
    }

    @PreDestroy
    public void destroy() {
        client.logout();
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

    private void registerEventDispatchers() {
        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(this.messageListener);
        dispatcher.registerListener(this.mentionListener);
    }
}
