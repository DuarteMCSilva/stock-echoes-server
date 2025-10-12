package com.stockechoes.services.utility.eventBuffer;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.MultiEmitter;
import lombok.Getter;

import java.time.Duration;
import java.util.List;

public class EventBuffer<T> {
    private final Duration DEFAULT_IDLE_TIME = Duration.ofSeconds(10);
    private final Duration DEFAULT_BATCH_BUFFER_MILLI = Duration.ofMillis(100);

    @Getter
    private final Integer batchSize;

    @Getter
    private final Duration debounceTime;

    @Getter
    private final Multi<List<T>> multi;

    private MultiEmitter<? super T> emitter;

    public EventBuffer(Integer batchSize, Integer debounceTime) {
        this.batchSize = batchSize;
        this.debounceTime = Duration.ofMillis(debounceTime);
        this.multi = this.buildMulti(batchSize, this.debounceTime, DEFAULT_IDLE_TIME);
    }

    public EventBuffer(Integer batchSize, Integer debounceTime, Duration idleTime) {
        this.batchSize = batchSize;
        this.debounceTime = Duration.ofMillis(debounceTime);
        this.multi = this.buildMulti(batchSize, this.debounceTime, idleTime);
    }

    public void emit(T value) {
        this.emitter.emit(value);
    }

    private Multi<List<T>> buildMulti(Integer batchSize, Duration debounceTime, Duration idleTime) {
        Multi<T> multi = Multi.createFrom().emitter(e -> this.emitter = e);

        Multi<List<T>> batchedMulti = this.batchMulti(multi, batchSize);

        if(debounceTime.isPositive()) {
            batchedMulti = addDebounceToMulti(batchedMulti, debounceTime);
        }

        return batchedMulti
                .onItem().invoke((batch) -> System.out.println("Batching: " + batch ))
                .ifNoItem().after(idleTime).recoverWithCompletion();
    }

    private Multi<List<T>> addDebounceToMulti(Multi<List<T>> multi, Duration debounceTime) {
        Multi<Long> ticks = Multi.createFrom().ticks().every(debounceTime)
                .onOverflow().drop();

        return Multi.createBy().combining().streams(ticks, multi).using( (_tick, batch) -> batch );
    }

    private Multi<List<T>> batchMulti(Multi<T> multi, Integer batchSize) {
        return multi.group().intoLists().of(batchSize, DEFAULT_BATCH_BUFFER_MILLI);
    }
}
