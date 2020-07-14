package students;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface Another {
  boolean doStuff(Student s);
}

interface StudentCriterion {
  boolean test(Student s);
  default void doStuff() {};
}

class SmartCriterion implements StudentCriterion {
  @Override
  public boolean test(Student s) {
    return s.getGrade() > 65;
  }
//  public void doStuff() {}
}

class EnthusiasticCriterion implements StudentCriterion {
  @Override
  public boolean test(Student s) {
    return s.getCourses().size() > 3;
  }
}

public class School {
  public static void showAll(List<Student> ls) {
    for (Student s : ls) {
      System.out.println("> " + s);
    }
    System.out.println("=====================");
  }

  public static List<Student> getStudentsByCriterion(List<Student> ls,
                                                     StudentCriterion crit) {
    List<Student> out = new ArrayList<>();
    for (Student s : ls) {
      if (crit.test(s)) {
        out.add(s);
      }
    }
    return out;
  }

//  public static List<Student> getSmartStudents(List<Student> ls, int threshold) {
//    List<Student> out = new ArrayList<>();
//    for (Student s : ls) {
//      if (s.getGrade() > threshold) {
//        out.add(s);
//      }
//    }
//    return out;
//  }
//
//  public static List<Student> getEnthusiasticStudents(List<Student> ls, int threshold) {
//    List<Student> out = new ArrayList<>();
//    for (Student s : ls) {
//      if (s.getCourses().size() > threshold) {
//        out.add(s);
//      }
//    }
//    return out;
//  }

  public static void main(String[] args) {
    List<Student> roster = Arrays.asList(
        Student.of("Fred", 70, "Math", "Physics"),
        Student.of("Jim", 58, "Art"),
        Student.of("Sheila", 92,
            "Math", "Physics", "Astrophysics", "Quantum Mechanics")
    );
//    showAll(getSmartStudents(roster, 65));
//    showAll(getEnthusiasticStudents(roster, 3));

    // command pattern (OO)
    // "Higher order function" (FP)
    showAll(getStudentsByCriterion(roster, new SmartCriterion()));
    showAll(getStudentsByCriterion(roster, new EnthusiasticCriterion()));
    // lambda expression (version 1)
//    showAll(getStudentsByCriterion(roster, (Student s) -> {
//      return s.getGrade() < 65;
//    } ));
//    showAll(getStudentsByCriterion(roster, (s) -> {
//      return s.getGrade() < 65;
//    } ));
    // block lambda
//    showAll(getStudentsByCriterion(roster, s -> {
//      return s.getGrade() < 65;
//    } ));
    // Expression lambda
    showAll(getStudentsByCriterion(roster, s -> s.getGrade() < 65));

    // NOT ALLOWED
//    Object criterion = s -> s.getGrade() < 65;
    StudentCriterion criterion;
    criterion = s -> s.getGrade() < 65;
    System.out.println("Class name of this lambda is " +
        criterion.getClass().getName());

//    Another & Serializable variable; NOT allowed for variables
    Object critObject = (Another & Serializable)(s -> s.getGrade() < 65);
    System.out.println("Class name of second lambda is " +
        criterion.getClass().getName()
        + "\n   instanceof StudentCriterion " + (critObject instanceof  StudentCriterion));



  }
}
