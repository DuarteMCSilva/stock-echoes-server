package com.stockechoes.resources.kafka;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class MessageConsumer {

    @Incoming("broadcast-channel")
    @Outgoing("uppercase-channel") // Use @Broadcast to have multiple consumers.
    public String consume(String message) {
        System.out.println(message + " - Consumed by MessageConsumer!");
        return message;
    }

    @Incoming("broadcast-channel")
    @Outgoing("uppercase-channel")
    public String consumer2(String message) {
        System.out.println(message + " - Consumed by MessageConsumer2!");
        return message;
    }
}
