package concordance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConcordanceStream {
  private static Pattern WORD_BOUNDARY = Pattern.compile("\\W+");

  public static void main(String[] args) {
    try (Stream<String> inStream =
             Files.lines(Paths.get("PrideAndPrejudice.txt"))) {
      inStream
          .filter(s -> s.length() > 0)
          .map(String::toLowerCase)
          .flatMap(WORD_BOUNDARY::splitAsStream)
          .filter(s -> s.length() > 0)
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
          .entrySet().stream()
//          .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
          .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
          .limit(200)
          .forEach(s -> System.out.println(s));

    } catch (IOException ioe) {
      System.out.println("File not found");
    }
  }
}
