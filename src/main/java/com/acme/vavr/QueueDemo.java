package com.acme.vavr;


import io.vavr.collection.Queue;

import javax.sound.midi.Soundbank;

public class QueueDemo {
  public static void main(String[] args) {
    Queue<Integer> q = Queue.of(1,2,3);
    System.out.println("q.peek() = " + q.peek());
    System.out.println("q: "+q);

    System.out.println("\nIterating over queue");
    q.forEach(System.out::println);

    //Polling
    System.out.println("\nPolling");
    var tuple = q.dequeue();
    System.out.println("tuple._1 = " + tuple._1);
    System.out.println("tuple._2 = " + tuple._2);
    System.out.println("q = " + q);


  }
}
