package stream;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

public class ParallelFailure {
//  static long myCount = 0;
//    static AtomicLong myCount = new AtomicLong(0);
    static LongAdder myCount = new LongAdder();
  public static void main(String[] args) {
    final long COUNT = 1_000_000_000;
    long start = System.nanoTime();
    Stream.generate(() -> 1)
        .parallel() // Sharding (partitioning) is expensive
        .limit(COUNT)
//        .peek(i -> myCount++)
        .peek(i -> myCount.increment())
        .reduce((a, b) -> a + b)
        .ifPresent(s -> System.out.println(
            "sum is " + s + " myCount is " + myCount));
    long time = System.nanoTime() - start;
    System.out.println("time " + (time / 1_000_000_000.0));
  }
}
