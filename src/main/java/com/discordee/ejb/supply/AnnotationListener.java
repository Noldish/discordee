package com.discordee.ejb.supply;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;

public class AnnotationListener {

    private final Logger logger = LogManager.getLogger("AnnotationListener");

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
        logger.info("ReadyEvent: " + event.toString());
    }
}
