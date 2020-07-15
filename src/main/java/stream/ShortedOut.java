package stream;

import java.util.Arrays;

public class ShortedOut {
  public static void main(String[] args) {
    long num = Arrays.asList(1,2,3)
        .stream()
        .peek(n -> System.out.println("> " + n))
        .count();
    System.out.println("num is " + num);
    boolean allmatch = Arrays.asList(1,2,3,4,5)
        .stream()
        .peek(n -> System.out.println("> " + n))
        .allMatch(n -> n % 2 != 0); // short circuit, like true && false && complex computation
    System.out.println("all match? " + allmatch);
    System.out.println("-=-------------");
    System.out.println("-=-------------");
    Arrays.asList(1,2,3,4,5)
        .stream()
        .peek(n -> System.out.println("> " + n))
        .forEach(System.out::println);
  }
}
