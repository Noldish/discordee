package com.discordee.ejb.listener;

import com.discordee.ejb.DiscordClient;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.MessageHistory;

@Stateless
public class MessageListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private DiscordClient discordClient;

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event) {
        if (event.getMessage().getContent().equals("test-history")) {
            IChannel channel = event.getGuild().getChannelByID(235804189240852480l);


//            Map<String, Integer> emojiiCounters = new HashMap<>();
//
//            Arrays.stream(history.asArray()).forEach(m -> {
//                String messageContent = m.getContent();
//
//                Map<String, Integer> emojiiInMessage = findAllEmojii(messageContent);
//
//                emojiiInMessage.forEach((k, v) -> {
//                    emojiiCounters.merge(k, v, (oldVal, newVal) -> oldVal + newVal);
//                });
//
//            });
//
//            event.getChannel().sendMessage(emojiiCounters.toString());
//

        }
    }

//    private Map<String, Integer> findAllEmojii(String messageText){
//        Pattern pattern = Pattern.compile("<:.*?:");
//        Matcher matcher = pattern.matcher(messageText);
//
//        Map<String, Integer> emojiiInMessage = new HashMap<>();
//
//        while(matcher.find()) {
//            emojiiInMessage.merge(matcher.group(0), 1, (oldVal, newVal) -> oldVal + newVal);
//        }
//
//        return emojiiInMessage;
//    }
}