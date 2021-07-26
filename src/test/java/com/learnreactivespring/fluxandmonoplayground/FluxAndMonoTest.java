package com.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest()
    {
        Flux<String> stringFlux=Flux.just("String","String Builder","String Buffer").log();
        stringFlux.subscribe(System.out::println,(e)->System.err.println("Error"),()-> System.out.println("Completed"));

    }

    @Test
    public void fluxTestWithoutError()
    {
        Flux<String> stringFlux=Flux.just("String","String Builder","String Buffer").log();
        StepVerifier.create(stringFlux)
                .expectNext("String")
                .expectNext("String Builder")
                .expectNext("String Buffer")
                .verifyComplete();
    }
    @Test
    public void fluxTestWithError()
    {
        Flux<String> stringFlux=Flux.just("String","String Builder","String Buffer")
                .concatWith(Flux.error(new RuntimeException("Error Occured")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectNext("String","String Builder","String Buffer")
                .expectNext("String")
                .expectNext("String Builder")
                .expectNext("String Buffer")
                .expectErrorMessage("Error Occured")
                /*.expectError(RuntimeException.class)*/
                .verify();
    }
}
