package averagereduce;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

class Average {
  private double sum;
  private long count;

  public Average(double sum, long count) {
    this.sum = sum;
    this.count = count;
  }

  public Average include(double d) {
    return new Average(this.sum + d, this.count + 1);
  }

  public Average combine(Average other) {
    return new Average(this.sum + other.sum, this.count + other.count);
  }

  public Optional<Double> get() {
    if (this.count > 0) {
      return Optional.of(this.sum / this.count); // avoid NaN... Sentinel value!!!
    } else {
      return Optional.empty();
    }
  }
}

public class Averager {
  public static void main(String[] args) {
    // Math.random() is a massive "critical section"
//    DoubleStream.generate(() -> ThreadLocalRandom.current().nextDouble(-Math.PI, Math.PI))
//        .limit(10)

    final long COUNT = 2_000_000_000;
    long start = System.nanoTime();

    ThreadLocalRandom.current().doubles(COUNT, -Math.PI, Math.PI)
        .mapToObj(d -> new Average(d, 1))
//        .reduce((a1, a2) -> a1.combine(a2))
        .reduce(Average::combine)
        .flatMap(Average::get)
        .ifPresent(a -> System.out.println("Average is " + a));

    long time = System.nanoTime() - start;
    System.out.printf("time for %d items is %7.3f seconds\n",
        COUNT, time / 1_000_000_000.0);
  }
}
