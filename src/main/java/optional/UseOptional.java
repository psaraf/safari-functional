package optional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UseOptional {
  public static void main(String[] args) {
    Map<String, String> names = new HashMap<>();
    names.put("Fred", "Jones");

    // computation produces a result:
    String first = "Freddy";

    // business logic
    String last = names.get(first);
    if (last != null) {
      String upper = last.toUpperCase();
      String message = "Dear " + upper;
      System.out.println(message);
    }

    Optional.ofNullable(names)
        .map(m -> m.get(first))
        .map(String::toUpperCase)
        .map(s -> "Dear " + s)
        .ifPresent(System.out::println);
  }
}
