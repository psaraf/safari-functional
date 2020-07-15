package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Concordance {
  private static Pattern WORD_BOUNDARY = Pattern.compile("\\W+");

  public static void main(String[] args) {
    SparkConf conf = new SparkConf()
        .setAppName("concordance")
        .setMaster("local");

    JavaSparkContext context = new JavaSparkContext(conf);

    // in general data in files must be accessible on the same path
    // to all nodes in the cluster
    context.textFile("PrideAndPrejudice.txt") // JavaRDD "resilient distributed dataset"
        // "resilient" recovery is simply, recompute
        // no need for persisting intermediate results (normal operation far faster)
        .filter(s -> s.length() > 0)
        .map(String::toLowerCase)
        .flatMap(l -> Arrays.asList(WORD_BOUNDARY.split(l)).iterator())
        .filter(w -> w.length() > 0)
        .mapToPair(w -> new Tuple2<>(w, 1L))
        .reduceByKey((v1, v2) -> v1 + v2) // NOT NOT NOT grouping! shuffling
        .mapToPair(p -> new Tuple2<>(p._2, p._1))
        .sortByKey(false)
        .map(t -> String.format("%20s : %5d", t._2, t._1)) // exectues remotely
        .take(200) // brings first 200 items back to the driver
        .forEach(System.out::println);

    context.close();
  }
}
