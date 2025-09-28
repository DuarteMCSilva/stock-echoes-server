package com.stockechoes.api.sandbox.messaging;

import io.smallrye.reactive.messaging.annotations.Merge;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class UppercaseConsumer {

    @Incoming("uppercase-channel")
    @Merge // Use @Merge to listen from multiple producers
    public void consume(String message) {
        System.out.println(message.toUpperCase() + " - Consumed by UppercaseConsumer!");
    }
}
