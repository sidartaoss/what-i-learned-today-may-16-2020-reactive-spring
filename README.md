# WILT - What I Learned Today

## Reference Documentation

For further reference, please consider the following sections:

* [Build Reactive RESTFUL APIs using Spring Boot/WebFlux](https://www.udemy.com/course/build-reactive-restful-apis-using-spring-boot-webflux/)


## WILT - May 16, 2020


What I learned today is about Section 7 of Dilip's course on Udemy about Reactive Programming using Spring Boot and WebFlux.

Thus, the first hands-on development of the course is performed via Test-Driven Development approach.



## Section 7: Reactive Programming (Flux and Mono) - Hands on + JUnit Testing

### 13. Flux - How it works

In this tutorial, we'll explore about Flux and Mono and its internals via code. Our approach to master the Reactor types Flux and Mono is by writing the test cases and learning from it.


```
public class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        final Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
//                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("After error"))
                .log();

        stringFlux
                .subscribe(System.out::println,
                        (e) -> System.err.println("Exception is: "+e),
                        () -> System.out.println("Completed"));
    }

} 
```


### 14. Flux - Learn to write JUnit Tests

In this tutorial, we'll learn about the techniques of writing the test cases that is going to assert on the elements that are part of the Flux.

```
    @Test
    public void fluxTestElements_WithoutError() {
        final Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete();
    }

    @Test
    public void fluxTestElements_WithError() {
        final Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
    //                .expectError(RuntimeException.class)
                .expectErrorMessage("Exception Occurred")
                .verify();
    }

    @Test
    public void fluxTestElementsCount_WithError() {
        final Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectErrorMessage("Exception Occurred")
                .verify();
    }

    @Test
    public void fluxTestElements_WithError1() {
        final Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
                .expectErrorMessage("Exception Occurred")
                .verify();
    }
```

### 15. Mono - How it works and write JUnit Tests

In this tutorial, we'll explore the Mono Reactor type. Let's go ahead and open the **FluxAndMonoTest** class.

```
    @Test
    public void monoTest() {
        final Mono<String> stringMono = Mono.just("Spring");

        StepVerifier.create(stringMono.log())
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void monoTest_Error() {
        StepVerifier.create(Mono.error(new RuntimeException("Exception Occurred")).log())
                .expectError(RuntimeException.class)
                .verify();
    }
``` 

### 16. Flux/Mono - Exploring Factory methods

In this tutorial, we'll explore some of the other factory methods that can be used to create Flux and Mono. For this purpose, what we're going to do is we're going to create a class called **FluxAndMonoFactoryTest**.

```
public class FluxAndMonoFactoryTest {

    private List<String> names = asList("Adam", "Anna", "Jack", "Jenny");

    @Test
    public void fluxUsingIterable() {

        final Flux<String> namesFlux = Flux.fromIterable(names)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Adam", "Anna", "Jack", "Jenny")
                .verifyComplete();
    }

    @Test
    public void fluxUsingArray() {
        String[] names = new String[] {"Adam", "Anna", "Jack", "Jenny"};

        final Flux<String> namesFlux = Flux.fromArray(names)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Adam", "Anna", "Jack", "Jenny")
                .verifyComplete();
    }

    @Test
    public void fluxUsingStream() {

        Flux<String> namesFlux = Flux.fromStream(names.stream())
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Adam", "Anna", "Jack", "Jenny")
                .verifyComplete();
    }

    @Test
    public void monoUsingJustOrEmpty() {
        final Mono<String> mono = Mono.justOrEmpty(null);// Mono.empty()

        StepVerifier.create(mono.log())
                .verifyComplete();
    }

    @Test
    public void monoUsingSupplier() {
        Supplier<String> stringSupplier = () -> "Adam";

        Mono<String> stringMono = Mono.fromSupplier(stringSupplier);

        System.out.println(stringSupplier.get());

        StepVerifier.create(stringMono.log())
                .expectNext("Adam")
                .verifyComplete();
    }

    @Test
    public void fluxUsingRange() {

        final Flux<Integer> integerFlux = Flux.range(1, 5);

        StepVerifier.create(integerFlux.log())
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }
}
```

### 17. Filtering a Reactive Stream

In this tutorial, we'll learn about how to do filtering operation using Project Reactor. So now we're going to create a class called **FluxAndMonoFilterTest**.

```
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
```

### 18. Transforming a Reactive Stream using map

In this tutorial, we will learn about how to transform a Flux from one form to another form using the **map** method. The first step is we'll going to create a test class with the name **FluxAndMonoTransformTest**.

```
public class FluxAndMonoTransformTest {

    private List<String> names = asList("adam", "anna", "jack", "jenny");

    @Test
    public void transformUsingMap() {
        Flux<String> namesFlux = Flux.fromIterable(names)
                .map(s -> s.toUpperCase())
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("ADAM", "ANNA", "JACK", "JENNY")
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_Length() {
        Flux<Integer> namesFlux = Flux.fromIterable(names)
                .map(s -> s.length())
                .log();

        StepVerifier.create(namesFlux)
                .expectNext(4, 4, 4, 5)
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_Length_repeat() {
        Flux<Integer> namesFlux = Flux.fromIterable(names)
                .map(s -> s.length())
                .repeat(1)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext(4, 4, 4, 5, 4, 4, 4, 5)
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_Filter() {
        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(s -> s.length() > 4)
                .map(s -> s.toUpperCase())
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("JENNY")
                .verifyComplete();
    }
}
```

### 19. Transforming a Reactive Stream using flatMap

In this tutorial, we will code and learn how to convert a Flux from one form to another form using the **flatMap** method. We're going to use the same test class that we used in the previous tutorial, which is **FluxAndMonoTransformTest**.

```
    @Test
    public void transformUsingFlatMap() {

        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))  // A, B, C, D, E, F
                .flatMap(s -> {
                    return Flux.fromIterable(convertToList(s)); // A -> List[A, newValue], B -> List[B, newValue]
                })      // Database call or external service call that returns a flux -> s -> Flux<String>
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

    }

    @Test
    public void transformUsingFlatMap_usingParallel() {

        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .window(2)      // Flux<Flux<String>> -> (A,B), (C,D), (E,F)
                .flatMap((s) ->
                            s.map(this::convertToList).subscribeOn(parallel()))   // Flux<List<String>>
                            .flatMap(s -> Flux.fromIterable(s))     // Flux<String>
                               // db or external service call that returns a flux -> s -> Flux<String>
                .log();


        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

    }

    @Test
    public void transformUsingFlatMap_parallel_maintain_order() {

        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .window(2)      // Flux<Flux<String>> -> (A,B), (C,D), (E,F)
//                .concatMap((s) ->
//                        s.map(this::convertToList).subscribeOn(parallel()))   // Flux<List<String>>
                .flatMapSequential((s) ->
                        s.map(this::convertToList).subscribeOn(parallel()))   // Flux<List<String>>
                .flatMap(s -> Flux.fromIterable(s))     // Flux<String>
                // db or external service call that returns a flux -> s -> Flux<String>
                .log();


        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();

    }

    private List<String> convertToList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s, "newValue");
    }
```

### 20. Combining Reactive Streams

In this tutorial, we will code and learn how to combine more than one publisher which is a Flux or Mono into one publisher, which is a Flux or Mono, using different operators that are available in Project Reactor. Now let's go ahead and create the test class. We're going to name this one as **FluxAndMonoCombineTest**.

```
public class FluxAndMonoCombineTest {

    @Test
    public void combineUsingMerge() {
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");

        Flux<String> mergedFlux = Flux.merge(flux1, flux2)
                .log();

        StepVerifier.create(mergedFlux)
                .expectSubscription()
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    public void combineUsingMerge_withDelay() {
        Flux<String> flux1 = Flux.just("A", "B", "C")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F")
                .delayElements(Duration.ofSeconds(1));

        Flux<String> mergedFlux = Flux.merge(flux1, flux2)
                .log();

        StepVerifier.create(mergedFlux)
                .expectSubscription()
                .expectNextCount(6)
//                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }


    @Test
    public void combineUsingConcat() {
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");

        Flux<String> mergedFlux = Flux.concat(flux1, flux2)
                .log();

        StepVerifier.create(mergedFlux)
                .expectSubscription()
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }
    @Test
    public void combineUsingConcat_withDelay() {

        VirtualTimeScheduler.getOrSet();

        Flux<String> flux1 = Flux.just("A", "B", "C")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F")
                .delayElements(Duration.ofSeconds(1));

        Flux<String> mergedFlux = Flux.concat(flux1, flux2)
                .log();

        StepVerifier.withVirtualTime(() -> mergedFlux)
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(6))
                .expectNextCount(6)
                .verifyComplete();


//        StepVerifier.create(mergedFlux)
//                .expectSubscription()
//                .expectNext("A", "B", "C", "D", "E", "F")
//                .verifyComplete();
    }

    @Test
    public void combineUsingZip() {
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");

        Flux<String> mergedFlux = Flux.zip(flux1, flux2, (t1, t2) -> {
            return t1.concat(t2);       // AD, BE, CF
        })    // A,D : B,E : C,F
        .log();

        StepVerifier.create(mergedFlux)
                .expectSubscription()
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }
}
```

### 21. Handling Errors in a Reactive Stream

In this tutorial, we will talk about how to handle any kind of error or exception that happens in the reactive sequence.

For that purpose, what we're going to do is we're going to create a class called **FluxAndMonoErrorTest**.

```
public class FluxAndMonoErrorTest {

    @Test
    public void fluxErrorHandling() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorResume((e) -> {     // this block gets executed and this is going to return the flux
                    System.out.println("Exception is: " + e);
                    return Flux.just("default", "default1");
                });

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
//                .expectError(RuntimeException.class)
//                .verify();
                .expectNext("default", "default1")
                .verifyComplete();
    }

    @Test
    public void fluxErrorHandling_OnErrorReturn() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorReturn("default");

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    public void fluxErrorHandling_OnErrorMap() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap((e) -> new CustomException(e));

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectError(CustomException.class)
                .verify();
    }

    @Test
    public void fluxErrorHandling_OnErrorMap_withRetry() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap((e) -> new CustomException(e))
                .retry(2);

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectError(CustomException.class)
                .verify();
    }

    @Test
    public void fluxErrorHandling_OnErrorMap_withRetryBackoff() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap((e) -> new CustomException(e))
                .retryBackoff(2, Duration.ofSeconds(5));

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectError(IllegalStateException.class)
                .verify();
    }
}
```

### 22. Infinite Reactive Stream

In this tutorial, we will go ahead and code how the Flux and Mono interacts with time. For this purpose what we're going to do is we will create a class called **FluxAndMonoWithTimeTest**.

```
public class FluxAndMonoWithTimeTest {

    @Test
    public void infiniteSequence() throws InterruptedException {
        final Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(100))
                .log();// starts from 0 --> ...

        infiniteFlux
                .subscribe(
                        (element) ->
                                System.out.println(
                                        String.format("Value is %s", element)));

        Thread.sleep(3000);

    }

    @Test
    public void infiniteSequenceTest() throws InterruptedException {
        final Flux<Long> finiteFlux = Flux.interval(Duration.ofMillis(100))
                .take(3)
                .log();// starts from 0 --> ...

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0L, 1L, 2L)
                .verifyComplete();
    }

    @Test
    public void infiniteSequenceMap() throws InterruptedException {
        final Flux<Integer> finiteFlux = Flux.interval(Duration.ofMillis(100))
                .map(l -> new Integer(l.intValue()))
                .take(3)
                .log();// starts from 0 --> ...

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0, 1, 2)
                .verifyComplete();
    }

    @Test
    public void infiniteSequenceMap_withDelay() throws InterruptedException {
        final Flux<Integer> finiteFlux = Flux.interval(Duration.ofMillis(100))
                .delayElements(Duration.ofSeconds(1))
                .map(l -> new Integer(l.intValue()))
                .take(3)
                .log();// starts from 0 --> ...

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0, 1, 2)
                .verifyComplete();
    }
}
```

### 23. Back Pressure - How it works?

In this tutorial, we will code and explore how to apply back pressure on data streams that are emitted by the publisher.

```
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
```

### 25. Hot versus Cold Reactive Streams

In this tutorial, we will code and learn what is a hot reactive stream and what is a cold reactive stream. For that purpose, what we're going to do is to create a test class with the name **ColdAndHotPublisherTest**.

```
public class ColdAndHotPublisherTest {

    @Test
    public void coldPublisherTest() throws InterruptedException {
        final Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1L))
                .log();

        stringFlux.subscribe(s -> System.out.println(String.format("Subscriber 1: %s", s)));

        Thread.sleep(2000);

        stringFlux.subscribe(s -> System.out.println(String.format("Subscriber 2: %s", s)));

        Thread.sleep(4000);

    }

    @Test
    public void hotPublisherTest() throws InterruptedException {
        final Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1L))
                .log();

        final ConnectableFlux<String> connectableFlux = stringFlux.publish();
        connectableFlux.connect();
        connectableFlux.subscribe(s -> System.out.println(String.format("Subscriber 1: %s", s)));
        Thread.sleep(3000);
        connectableFlux.subscribe(s -> System.out.println(String.format("Subscriber 2: %s", s))); // does not emit the values from beginning
        Thread.sleep(4000);

    }
}
```

### 26. Virtualizing Time in JUnit

In this tutorial, we will code and learn how to virtualize the time when running the test cases. For this purpose, what we're going to do is to create a class called **VirtualTimeTest**.

``` 
public class VirtualTimeTest {

    @Test
    public void testingWithoutVirtualTime() {
        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1))
                .take(3)
                .log();

        StepVerifier.create(longFlux)
                .expectSubscription()
                .expectNext(0L, 1L, 2L)
                .verifyComplete();

    }

    @Test
    public void testingWithVirtualTime() {
        VirtualTimeScheduler.getOrSet();

        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1))
                .take(3)
                .log();

        StepVerifier.withVirtualTime(() -> longFlux)
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(3))
                .expectNext(0L, 1L, 2L)
                .verifyComplete();

    }
}
``` 

