package com.acme.vavr;

import io.vavr.collection.CharSeq;
import io.vavr.control.Validation;

public class ValidatorDemo {
  public static void main(String[] args) {

    var invalidLetters = validateLettersOnly("a 1 2 a a a 3 3 3 3 3 ");
    System.out.println("invalidLetters = " + invalidLetters);
    var validLetters = validateLettersOnly("AsadlfkjasdJDSJlskdjflsdkfçÇÇ");
    System.out.println("validLetters = " + validLetters);

    var invalidAge = validateAdultsOnly(17);
    System.out.println("invalidAge = " + invalidAge);

    var validAge = validateAdultsOnly(19);
    System.out.println("validAge = " + validAge);

    var combined = Validation.combine(validateAdultsOnly(17),validateLettersOnly("Rafael 17"));
    System.out.println("combined = " + combined.ap((integer, s) -> integer + ":"+s));

    combined = Validation.combine(validateAdultsOnly(27),validateLettersOnly("Rafael"));
    System.out.println("combined = " + combined.ap((integer, s) -> integer + ":"+s));
  }

  private static Validation<String, String> validateLettersOnly(String value){
    final String validChars = "[a-zA-Z]";
    return CharSeq.of(value).replaceAll(validChars,"")
      .transform(seq -> seq.isEmpty() ?
          Validation.valid(value) :
          Validation.invalid("The following char(s) is(are) not letters: ["+ seq.distinct().sorted() +"]")
        );
  }

  public static final Validation<String,Integer> validateAdultsOnly(Integer value){
    if(value>17){
      return Validation.valid(value);
    }
    return Validation.invalid("Adults only. Age informed is "+value);
  }


}
