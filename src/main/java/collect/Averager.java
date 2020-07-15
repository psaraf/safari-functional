package collect;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

class Average {
  private double sum;
  private long count;

  public Average(double sum, long count) {
    this.sum = sum;
    this.count = count;
  }

  public void include(double d) {
    this.sum += d;
    this.count++;
  }

  public void combine(Average other) {
    this.sum += other.sum;
    this.count += other.count;
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
    final long COUNT = 5_000_000_000L;
    long start = System.nanoTime();

    ThreadLocalRandom.current().doubles(COUNT, -Math.PI, Math.PI)
        .parallel()
        .collect(
            () -> new Average(0, 0),
            (a, d) -> a.include(d),
            (aResult, aNother) -> aResult.combine(aNother))
        .get()
        .ifPresent(a -> System.out.println("Average is " + a));

    long time = System.nanoTime() - start;
    System.out.printf("Average2: time for %d items is %7.3f seconds\n",
        COUNT, time / 1_000_000_000.0);
  }

}
