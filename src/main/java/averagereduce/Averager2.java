package averagereduce;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class Averager2 {
  public static void main(String[] args) {
    final long COUNT = 2_000_000_000;
    long start = System.nanoTime();

    ThreadLocalRandom.current().doubles(COUNT, -Math.PI, Math.PI)

//    DoubleStream.generate(
//        ()->ThreadLocalRandom.current().nextDouble(-Math.PI, Math.PI))

//    DoubleStream.iterate(0.0,
//        x -> ThreadLocalRandom.current().nextDouble(-Math.PI, Math.PI))

//        .limit(COUNT)

        .parallel()
//        .sequential()
        .boxed()
        .reduce(
            new Average(0, 0),
            (a, d) -> a.include(d),
            (aResult, aNother) -> aResult.combine(aNother))
        .get()
        .ifPresent(a -> System.out.println("Average is " + a));

    long time = System.nanoTime() - start;
    System.out.printf("Average2: time for %d items is %7.3f seconds\n",
        COUNT, time / 1_000_000_000.0);
  }
}
