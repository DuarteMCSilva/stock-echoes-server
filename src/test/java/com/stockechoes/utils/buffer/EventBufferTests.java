package com.stockechoes.utils.buffer;


import com.stockechoes.services.utility.eventBuffer.EventBuffer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventBufferTests {

    @Test
    @DisplayName("Should group events into batches")
    void buffer_group() {
        // given
        ArrayList<List<Integer>> outputList = new ArrayList<>();
        EventBuffer<Integer> eventBuffer = new EventBuffer<>(2, 0);

        // when
        eventBuffer.getMulti().subscribe().with(outputList::add);
        eventBuffer.emit(112);
        eventBuffer.emit(2);
        eventBuffer.emit(554);
        eventBuffer.emit(71);

        // then
        await().atMost(Duration.ofMillis(300)).until( () -> outputList.size() == 2);
        assertEquals(List.of(112, 2), outputList.getFirst());
        assertEquals(List.of(554,71), outputList.get(1));
    }

    @Test
    @DisplayName("Should emit batches one at a time, as defined by debounce")
    void buffer_debounce() {
        // given
        int debounceMillis = 200;
        ArrayList<List<Integer>> outputList = new ArrayList<>();
        EventBuffer<Integer> eventBuffer = new EventBuffer<>(3, debounceMillis);

        // when
        eventBuffer.getMulti().subscribe().with(outputList::add);
        eventBuffer.emit(112);
        eventBuffer.emit(2);
        eventBuffer.emit(554);
        eventBuffer.emit(71);

        // then
        await().atMost(Duration.ofMillis(150)).until( () -> outputList.size() == 1); // first batch
        assertEquals(List.of(112, 2, 554), outputList.getFirst());

        await().atMost(Duration.ofMillis(250)).until( () -> outputList.size() == 2); // second batch
        assertEquals(List.of(71), outputList.get(1));
    }

    @Test
    @DisplayName("Should complete after idle time")
    void buffer_complete() {
        // given
        int debounceMillis = 200;
        Duration idleTime = Duration.ofSeconds(1);
        EventBuffer<Integer> eventBuffer = new EventBuffer<>(3, debounceMillis,idleTime);

        AtomicBoolean completed = new AtomicBoolean(false);
        // when
        eventBuffer.getMulti().subscribe().with(
                System.out::println,
                ()-> completed.set(true)
        );

        eventBuffer.emit(1);

        await().atMost(Duration.ofSeconds(2)).until(completed::get);
    }

}