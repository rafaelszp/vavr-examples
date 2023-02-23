package com.acme.vavr;

import io.vavr.control.Either;

import java.util.Random;
import java.util.stream.IntStream;

public class EitherDemo {

  public static void main(String[] args) {
    IntStream.range(1, 10).forEach(i -> System.out.printf("Result of maybeGetNumber(): %s\n", failIfOdd().getOrElseGet((e)->new Result(e.getMessage()))));
  }

  public static Either<RuntimeException, Result> failIfOdd() {
    Random rnd = new Random();
    final int i = rnd.nextInt(1000);
    var bool = i % 2 == 0;
    if (!bool) {
      return Either.left(new RuntimeException("Oh! No! It's a Galore error: " + i));
    }
    return Either.right(new Result(i));
  }

  public static class Result{
    public Integer value;
    public String error;

    public Result(Integer value) {
      this.value = value;
    }

    public Result(String error) {
      this.error = error;
    }

    @Override
    public String toString() {
      var err = error!=null ?  "error='" + error + '\'':"";
      var val = value !=null ? "value=" + value:"" ;
      return "Result{" +
        val +
        err +
        '}';
    }
  }




}
