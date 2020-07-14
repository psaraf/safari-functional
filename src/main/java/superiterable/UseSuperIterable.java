package superiterable;

import java.util.Arrays;
import java.util.List;

public class UseSuperIterable {
  public static void main(String[] args) {
    SuperIterable<String> sis = new SuperIterable<>(
        Arrays.asList("Fred", "Jim", "Sheila")
    );

    sis
        .filter(s -> s.length() > 3)
        .map(s -> s.length())
        .forEach(s -> System.out.println("> " + s));

    sis
        .map(s -> "Welcome " + s.toUpperCase())
        .forEach(s -> System.out.println(s));

    SuperIterable<Student> roster = new SuperIterable<>(Arrays.asList(
        Student.of("Fred", 70, "Math", "Physics"),
        Student.of("Jim", 58, "Art"),
        Student.of("Sheila", 92,
            "Math", "Physics", "Astrophysics", "Quantum Mechanics")
    ));

    roster
        .filter(s -> s.getGrade() > 60)
        .map(s -> s.getName() + " has a grade of " + s.getGrade() +
            " and takes " + s.getCourses().size() + " courses")
        .forEach(s -> System.out.println(s));

    roster
        .flatMap(s -> new SuperIterable<>(s.getCourses()))
        .forEach(s -> System.out.println(s));
  }
}
