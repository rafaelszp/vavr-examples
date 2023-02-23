package com.acme.vavr;

import io.vavr.Function1;
import io.vavr.concurrent.Future;

import java.time.*;
import java.time.temporal.ChronoField;
import java.util.List;

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
