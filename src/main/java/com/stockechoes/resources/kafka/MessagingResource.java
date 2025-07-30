package com.stockechoes.resources.kafka;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

@Path("/message")
public class MessagingResource {

    private final Emitter<String> emitter;

    // Use @Broadcast to have multiple consumers.
    public MessagingResource(
            @Channel("broadcast-channel") @Broadcast Emitter<String> emitter
    ) {
        this.emitter = emitter;
    }

    @POST
    @Consumes("text/plain")
    public String postMessage(final String text) {
        final String payload =  text + " World!";
        final var message = Message.of(payload).addMetadata(System.currentTimeMillis());

        this.emitter.send(message);
        return "message sent!";
    }
}
