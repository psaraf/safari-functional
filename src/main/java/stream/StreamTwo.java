package stream;

import java.util.Optional;
import java.util.stream.Stream;

public class StreamTwo {
  public static void main(String[] args) {
    Integer result = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .reduce(0, (a, b) -> a + b);
    System.out.println(result);

    /*Optional<Integer> os = */Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .reduce((a, b) -> a + b)
        .ifPresent(a -> System.out.println("sum is " + a));
  }
}
