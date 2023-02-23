package com.acme.vavr;

import io.vavr.control.Try;


import static io.vavr.Predicates.*;
import java.util.Random;

import static io.vavr.API.*;        // $, Case, Match

public class TryDemo {

    public static void main(String... args){
        Integer def = 0;

        var res = Try.of(TryDemo::mightFail)
            .recover(e -> Match(e.getClass().getName()).of(
                Case($(is(RuntimeException.class.getName())),-1),
                Case($(),1)
            )).getOrElse(def);

        System.out.println(res);

        //Simple Try

        var simple = Try.of(TryDemo::mightFail).getOrElse(-1);
        System.out.println(simple);

        var result = willFail().getOrElseGet((l)->new Result(l.getMessage()));
        System.out.println("result = " + result.error);


    }

    public static Integer mightFail(){

        Random rnd = new Random();
        var result = rnd.nextInt(1000);
        if(result % 2 == 0 ){
           throw new RuntimeException("Only odd numbers allowed");
        }
        return Integer.valueOf(result);
    }

    public static Try<Result> willFail(){
        return Try.failure(new RuntimeException("Failed on purpose"));
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
    }

}