package com.acme.vavr;

import io.vavr.Function1;
import io.vavr.Lazy;
import io.vavr.concurrent.Future;

import java.time.*;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Random;

public class FutureDemo {
  public static void main(String[] args) {

    Function1<Void, Long> now = (v) -> LocalDateTime.now().getLong(ChronoField.NANO_OF_SECOND);

    System.out.println(now.apply(null));
    System.out.println(now.apply(null));

    var fu1 = Future.ofSupplier(() -> now.apply(null));
    System.out.println("fu1 = " + fu1.get());
    fu1.await();

    Function1<Void, String> nowStr = sequenceExample(now);
    futureReduce(nowStr);

    var bef = System.currentTimeMillis();
    lazySequencesWithDelay();
    var aft = System.currentTimeMillis();
    System.out.println("aft = " + (aft-bef));

  }

  private static void lazySequencesWithDelay() {

    Function1<Void,Integer> delayedRetrieve = v -> {
      var delay = new Random().nextInt(5000);
      try {
        Thread.sleep(delay);
      } catch (InterruptedException e) {
      }
      return delay;
    };

    var list = List.of(
      Future.ofSupplier(()->delayedRetrieve.apply(null)+""),
      Future.ofSupplier(()->delayedRetrieve.apply(null)+""),
      Future.ofSupplier(()->delayedRetrieve.apply(null)+""),
      Future.ofSupplier(()->delayedRetrieve.apply(null)+""),
      Future.ofSupplier(()->delayedRetrieve.apply(null)+""),
      Future.ofSupplier(()->delayedRetrieve.apply(null)+""),
      Future.ofSupplier(()->delayedRetrieve.apply(null)+""),
      Future.ofSupplier(()->delayedRetrieve.apply(null)+""),
      Future.ofSupplier(()->delayedRetrieve.apply(null)+"")
    );

    var f = Future.reduce(list,(a,b)->a+","+b);
    System.out.println("async reduce with delay = " + f.get());
  }

  private static Function1<Void, String> sequenceExample(Function1<Void, Long> now) {
    var list = List.of(
      Future.ofSupplier(() -> now.apply(null)),
      Future.ofSupplier(() -> now.apply(null)),
      Future.ofSupplier(() -> now.apply(null)),
      Future.ofSupplier(() -> now.apply(null))
    );

    Function1<Void, String> nowStr = (v) -> LocalDateTime.now().getLong(ChronoField.MILLI_OF_SECOND) + "";

    var fs = Future.sequence(list);
    fs.get().forEach(System.out::println);

    fs.await();
    return nowStr;
  }

  private static void futureReduce(Function1<Void, String> nowStr) {
    var fu2 = Future.reduce(
      List.of(
        Future.ofSupplier(() -> nowStr.apply(null)),
        Future.ofSupplier(() -> nowStr.apply(null)),
        Future.ofSupplier(() -> nowStr.apply(null)),
        Future.ofSupplier(() -> nowStr.apply(null)),
        Future.ofSupplier(() -> nowStr.apply(null)),
        Future.ofSupplier(() -> nowStr.apply(null))
      )
      , (a, b) -> a + "," + b);
    System.out.println("fu2 = " + fu2.get());
  }
}
