package superiterable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class StreamOne {

  public static <E> Predicate<E> negate(Predicate<E> criterion) {
    return e -> {
      return !criterion.test(e);
    };
  }

  public static <E> Predicate<E> and(Predicate<E> first, Predicate<E> second) {
    return e -> {
      return first.test(e) && second.test(e);
    };
  }

  public static <E> Predicate<E> or(Predicate<E> first, Predicate<E> second) {
    return e -> {
      return first.test(e) || second.test(e);
    };
  }

  public static void main(String[] args) {
    List<Student> rosterList = Arrays.asList(
        Student.of("Fred", 70, "Math", "Physics"),
        Student.of("Jim", 58, "Art"),
        Student.of("Sheila", 92,
            "Math", "Physics", "Astrophysics", "Quantum Mechanics")
    );

    rosterList.stream()
        .filter(
            and(
                negate(Student.getSmartCriterion(75)),
                s -> s.getCourses().size() > 1)
        )
        .forEach(System.out::println);

    System.out.println("---------------");
    rosterList.stream()
        .filter(Student.getSmartCriterion(75).negate()
            .and(s -> s.getCourses().size() > 1))
        .forEach(System.out::println);
  }
}
