package com.discordee.ejb;

import com.discordee.ejb.DiscordClient;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MentionEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;

@Stateless
public class DiscordMessageListener {

    private final Logger logger = LogManager.getLogger("DiscordMessageListener");

    @Inject
    private DiscordClient discordClient;

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event) {
        //logger.info(event.getAuthor().getDiscriminator());
    }

}