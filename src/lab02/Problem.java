/*
    Author: Mutu Gheorghita
*/

package lab02;

import java.util.ArrayList;
import java.util.List;

public class Problem {
    private List<Student> students;
    private List<Teacher> teachers;


    Problem() {
        teachers = new ArrayList<>();
        students = new ArrayList<>();
    }

    void setStudents(List<Student> students) {
        boolean exists;
        for (Student student : students) {
            exists = false;
            for (Student student1 : this.students) {
                if (student.equals(student1)) {
                    System.out.println("Can't add the same student twice to the same problem!");
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                this.students.add(student);
            }
        }
    }

    void setTeachers(List<Teacher> teachers) {
        boolean exists;
        for (Teacher teacher : teachers) {
            exists = false;
            for (Teacher teacher1 : this.teachers) {
                if (teacher.equals(teacher1)) {
                    System.out.println("Can't add the same teacher twice!");
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                this.teachers.add(teacher);
            }
        }
    }

    public String toString() {
        StringBuilder problem = new StringBuilder();

        problem.append("Students preferences\n");
        for (Student student : this.students) {
            problem.append(student);
            problem.append("\n");
        }
        problem.append("\n");

        problem.append("Teachers preferences and available projects\n");
        for (Teacher teacher : this.teachers) {
            problem.append(teacher);
            problem.append("\n");
        }
        problem.append("\n");

        return problem.toString();
    }

    List<Student> getStudents() {
        return this.students;
    }
}
