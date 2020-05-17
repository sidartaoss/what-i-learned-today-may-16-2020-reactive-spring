package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static java.util.Arrays.asList;

public class FluxAndMonoFilterTest {

    private List<String> names = asList("Adam", "Anna", "Jack", "Jenny");

    @Test
    public void filterTest() {

        final Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(s -> s.startsWith("A"))
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Adam", "Anna")
                .verifyComplete();
    }

    @Test
    public void filterTestLength() {

        final Flux<String> namesFlux = Flux.fromIterable(names)     // Adam, Anna, Jack, Jenny
                .filter(s -> s.length() > 4)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Jenny")
                .verifyComplete();
    }
}
