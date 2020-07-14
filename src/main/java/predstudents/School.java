package predstudents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class School {
  public static <E> void showAll(List<E> ls) {
    for (E s : ls) {
      System.out.println("> " + s);
    }
    System.out.println("=====================");
  }

  public static <E> List<E> filter(
      Iterable<E> ls, Predicate<E> crit) {
    List<E> out = new ArrayList<>();
    for (E s : ls) {
      if (crit.test(s)) {
        out.add(s);
      }
    }
    return out;
  }

  public static void main(String[] args) {
    List<Student> roster = Arrays.asList(
        Student.of("Fred", 70, "Math", "Physics"),
        Student.of("Jim", 58, "Art"),
        Student.of("Sheila", 92,
            "Math", "Physics", "Astrophysics", "Quantum Mechanics")
    );
    showAll(filter(roster, s -> s.getGrade() < 65));

    showAll(filter(Arrays.asList("Fred", "Jim", "Sheila"),
        s -> s.length() > 3));
  }
}
