package predstudents;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Student {
  private String name;
  private int grade;
  private List<String> courses;

  private Student(String name, int grade, List<String> courses) {
    this.name = name;
    this.grade = grade;
    this.courses = courses;
  }

  public static Student of(String name, int grade, String ... courses) {
    return new Student(name, grade,
        Collections.unmodifiableList(Arrays.asList(courses)));
                                  // List.of truly immutable list :)
  }

  public String getName() {
    return name;
  }

  public int getGrade() {
    return grade;
  }

  public Student setGrade(int g) {
    return new Student(this.name, g, this.courses);
  }

  public List<String> getCourses() {
    return courses;
  }

  @Override
  public String toString() {
    return "Student{" +
        "name='" + name + '\'' +
        ", grade=" + grade +
        ", courses=" + courses +
        '}';
  }

  public static Predicate<Student> getSmartCriterion() {
    return s -> s.getGrade() < 65;
  }
}
