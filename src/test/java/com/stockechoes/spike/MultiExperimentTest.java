package com.stockechoes.spike;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.subscription.MultiEmitter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiExperimentTest {

    private MultiEmitter<? super Integer> emitter;

    @Test
    @DisplayName("Multi Experiment - should aggregate each 2 elements")
    @Disabled
    void multi() {
        List<Integer> outputList = new ArrayList<>();


        Multi<Integer> multi = Multi.createFrom().emitter(e -> this.emitter = e);

        multi.group().intoLists().of(2)
                .subscribe()
                .with(
                item -> outputList.add(item.getFirst()),
                        System.out::println,
                () -> System.out.println("Done! ")
        );

        emitter.emit(1);
        emitter.emit(2);
        emitter.emit(4);
        emitter.emit(6);
        emitter.emit(11);
        //emitter.complete();

        assertEquals(1, outputList.getFirst());
        assertEquals(4, outputList.get(1));
        //assertEquals(11, outputList.get(2)); // With complete, the last one is emitted!

    }

    @Test
    @DisplayName("Multi Experiment - should aggregate each 2 elements")
    @Disabled
    void multi_debouced() throws InterruptedException {

        List<Integer> outputList = new ArrayList<>();
        int millis = 200;

        Multi<Integer> multi = Multi.createFrom().emitter(e -> this.emitter = e);
        Multi<Integer> debouncedMulti = addDebounce(multi, Duration.ofMillis(millis));

        AtomicReference<Boolean> complete = new AtomicReference<>(false);
        debouncedMulti.subscribe().with(
                outputList::add,
                System.out::println,
                () -> {
                    System.out.println("Done! ");
                    complete.set(true);
                }
        );

        emitter.emit(1);
        emitter.emit(2);
        emitter.emit(554);
        emitter.emit(7);
        emitter.complete();

        Thread.sleep(Duration.ofMillis(50));
        Thread.sleep(Duration.ofMillis(millis));

        assertEquals(1, outputList.size());
        Thread.sleep(Duration.ofMillis(millis));

        assertEquals(2, outputList.size());

        await().atMost(Duration.ofSeconds(2)).until(complete::get);
    }

    Multi<Integer> addDebounce(Multi<Integer> multi, Duration duration) {
        return multi.onItem().transformToUniAndConcatenate(batch ->
                        Uni.createFrom().item(batch)
                                .onItem().delayIt().by(duration)  // delay *before* API call
                );
    }

    Multi<List<Integer>> batchMulti(Multi<Integer> multi) {
        return multi.group().intoLists().of(2);
    }
}
