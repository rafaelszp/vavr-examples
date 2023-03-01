package com.acme.vavr;

import io.vavr.collection.List;
import io.vavr.collection.Stream;

public class SeqDemo {

  public static void main(String[] args) {

    var ints = Stream.range(1,10);
    System.out.println("ints.toList() = " + ints.toList());

    var csv = ints.map(String::valueOf).mkString(", ");
    System.out.println("csv  = " + csv);

    var csv2 = ints.mkCharSeq(", ");
    System.out.println("csv2 = " + csv2);

    var t = csv2.transform(characters -> characters.toUpperCase());
    System.out.println("t = " + t);

   var quotedJsonArray = ints.mkCharSeq("['","','","']").mkString();
    System.out.println("quotedJsonArray = " + quotedJsonArray);

  }

}
