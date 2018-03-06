/*
    Author: Mutu Gheorghita
*/

package lab02;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Teacher extends Person {

    private List<Student> students = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();
    private int leftCapacity;
    private int capacity;


    public Teacher(String name, String email, int capacity) {
        super(name, email);
        this.capacity = capacity;
        this.leftCapacity = capacity;
    }

    public Project createProject(String name, int capacity) {
        Project project = new Project(name, capacity, this);
        projects.add(project);
        return project;
    }

    public void setPreferences(List<Student> students) {
        boolean exists;
        for (Student student : students) {
            exists = false;
            for (Student student1 : this.students) {
                if (student.equals(student1)) {
                    System.out.println("Can't add the same student twice!");
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                this.students.add(student);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Teacher ").append(getName()).append(" with max capacity ");
        sb.append(capacity).append(" and email ");
        sb.append(getEmail()).append(", has this projects proposed: ");
        boolean first = true;
        for (Project project : projects) {
            if (!first) {
                sb.append(", ");
            }

            sb.append(project.getName());
            first = false;

        }

        sb.append(".\nHis preferences are: ");
        first = true;
        for (Student student : students) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(student.getName());
            first = false;
        }
        return sb.toString();
    }

    @Override
    public boolean isFree() {
        return leftCapacity > 0;
    }

    public void setLeftCapacity(int leftCapacity) {
        this.leftCapacity = leftCapacity;
    }

    public int getLeftCapacity() {
        return this.leftCapacity;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public List<Student> getAssignedStudents() {
        HashSet<Student> students = new HashSet<>();
        for (Project project : projects) {
            students.addAll(project.getStudents());
        }
        return new ArrayList<>(students);
    }
}
