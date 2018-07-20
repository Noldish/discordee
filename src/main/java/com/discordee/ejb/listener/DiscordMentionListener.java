package com.discordee.ejb.listener;

import com.discordee.ejb.DiscordClient;
import javax.ejb.Stateless;
import javax.inject.Inject;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MentionEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;

@Stateless
public class DiscordMentionListener {

    @Inject
    private DiscordClient discordClient;

    @EventSubscriber
    public void onMentionEvent(MentionEvent event) {
        IMessage message = event.getMessage();
        discordClient.addReaction(message);
        
        discordClient.replyAbountWeather(event.getMessage());
    }

}