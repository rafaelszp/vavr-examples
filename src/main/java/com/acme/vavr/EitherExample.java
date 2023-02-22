package com.acme.vavr;

import java.util.Random;

import io.vavr.control.Either;

public class EitherExample {

    public static void main(String... args){

        var value = mightFail();
        System.out.println(value);

    }

    public static Either<String,Integer> mightFail(){
        Random rnd = new Random();
        var result = rnd.nextInt(1000);
        if(result % 2 == 0 ){
            return Either.left("Only odd numbers allowed");
        }
        return Either.right(result);
    }
    
}
