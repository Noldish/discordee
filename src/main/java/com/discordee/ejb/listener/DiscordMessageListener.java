package com.discordee.ejb.listener;

import com.discordee.ejb.DiscordClient;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.MessageHistory;

@Stateless
public class DiscordMessageListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private DiscordClient discordClient;

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event) {
        if (event.getMessage().getContent().equals("test-history")) {
            MessageHistory history = event.getChannel().getFullMessageHistory();
            System.out.println(1);
        }
    }

}