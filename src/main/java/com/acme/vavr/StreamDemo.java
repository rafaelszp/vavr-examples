package com.acme.vavr;

import io.vavr.Function1;
import io.vavr.collection.Stream;

import java.math.BigDecimal;
import java.util.function.Function;

public class StreamDemo {

  public static void main(String[] args) {

    var ints = Stream.of(1,2,3);
    System.out.println("ints = " + ints.toList());

    Function<Integer,Integer> timesTwo = i -> i*2;
    var doubledInts = ints.map(timesTwo);
    System.out.println("doubledInts = " + doubledInts.toList());

    Function1<Integer, BigDecimal> divideByTwo = i -> BigDecimal.valueOf(i).divide(BigDecimal.valueOf(2)).setScale(4);
    var halved = ints.map(divideByTwo);
    System.out.println("halved = " + halved.toList());

  }
}
