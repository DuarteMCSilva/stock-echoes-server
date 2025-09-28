package com.stockechoes.api.sandbox.messaging;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class MessageConsumer {

    @Incoming("broadcast-channel")
    @Outgoing("uppercase-channel") // Use @Broadcast to have multiple consumers.
    public Uni<Message<String>> consume(Message<String> message) {
        final var payload = message.getPayload();
        final var metadata = message.getMetadata(Long.class);
        metadata.ifPresentOrElse(
                (a) -> System.out.println(payload + " - Consumed by MessageConsumer! - " + a),
                () -> System.out.println(payload + " - Consumed by MessageConsumer! " )
        );
        // When working with Message object, we must acknowledge
        return Uni.createFrom().item(Message.of(payload));
    }

    @Incoming("broadcast-channel")
    @Outgoing("uppercase-channel")
    public String consumer2(String message) {
        System.out.println(message + " - Consumed by MessageConsumer2!");
        return message;
    }
}
