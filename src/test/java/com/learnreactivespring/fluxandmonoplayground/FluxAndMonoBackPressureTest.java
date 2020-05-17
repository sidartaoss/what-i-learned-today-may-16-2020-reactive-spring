package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoBackPressureTest {

    @Test
    public void backPressureTest() {

        final Flux<Integer> finiteFlux = Flux.range(1, 10)
                .log();

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(1)
                .expectNext(2)
                .thenCancel()
                .verify();

    }

    @Test
    public void backPressure() {
        final Flux<Integer> finiteFlux = Flux.range(1, 10)
                .log();

        finiteFlux.subscribe(
                (element) -> System.out.println(String.format("Element is %s", element)),
                (exception) -> System.err.println(String.format("Exception is %s", exception)),
                () -> System.out.println("Done"),
                (subscription -> subscription.request(2)));


    }

    @Test
    public void backPressure_cancel() {
        final Flux<Integer> finiteFlux = Flux.range(1, 10)
                .log();

        finiteFlux.subscribe(
                (element) -> System.out.println(String.format("Element is %s", element)),
                (exception) -> System.err.println(String.format("Exception is %s", exception)),
                () -> System.out.println("Done"),
                (subscription -> subscription.cancel()));


    }

    @Test
    public void customized_backPressure() {

        final Flux<Integer> finiteFlux = Flux.range(1, 10)
                .log();

        finiteFlux.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnNext(Integer value) {
                request(1);
                System.out.println(String.format("Value received is %d", value));
                if (value.intValue() == 4) {
                    cancel();
                }
            }
        });
    }


}
