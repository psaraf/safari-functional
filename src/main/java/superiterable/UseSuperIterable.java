package superiterable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UseSuperIterable {
  public static void main(String[] args) {
    SuperIterable<String> sis = new SuperIterable<>(
        Arrays.asList("Fred", "Jim", "Sheila")
    );

    sis
        .filter(s -> s.length() > 3)
//      .map(s -> s.length())
        .map(String::length)
        .forEach(s -> System.out.println("> " + s));

    sis
        .map(s -> "Welcome " + s.toUpperCase())
        .forEach(s -> System.out.println(s));

    List<Student> rosterList = Arrays.asList(
        Student.of("Fred", 70, "Math", "Physics"),
        Student.of("Jim", 58, "Art"),
        Student.of("Sheila", 92,
            "Math", "Physics", "Astrophysics", "Quantum Mechanics")
    );

    SuperIterable<Student> roster = new SuperIterable<>(rosterList);

    roster
        .filter(s -> s.getGrade() > 60)
        .map(s -> s.getName() + " has a grade of " + s.getGrade() +
            " and takes " + s.getCourses().size() + " courses")
        .forEach(s -> System.out.println(s));

    roster
        .flatMap(s -> new SuperIterable<>(s.getCourses()))
//        .forEach(s -> System.out.println(s))
    ;

    Function<Student, String> fss = s -> {
      System.out.println("transformer...");
      return s.getName() + " has grade " + s.getGrade();
    };

    rosterList.stream()
        .flatMap(s -> s.getCourses().stream())
        .forEach(s -> System.out.println(s));

    System.out.println("------------- super iterable ------------");
    SuperIterable<String> sistr = roster
        .map(fss)
//        .forEach(s -> System.out.println(s))
    ;

    sistr.forEach(s -> System.out.println(s));
    System.out.println("---");
    sistr.forEach(s -> System.out.println(s));

    System.out.println("------------- stream ------------");
    List<String> ss = rosterList.stream()
        .map(fss)
//        .forEach(s -> System.out.println(s))
        .collect(Collectors.toList());
    ;

    ss.stream().forEach(s -> System.out.println(s));
    ss.stream().forEach(System.out::println);

  }
}
