/*
    Author: Mutu Gheorghita
*/

package lab02;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Project {
    private String name;
    private int capacity;
    private int leftCapacity;
    private Teacher teacher;
    private List<Student> students;

    Project(String name, int capacity, Teacher teacher) {
        if (capacity <= 0) {
            System.out.println("Capacity can't be 0 or lower!");
            System.exit(0);
        }

        this.name = name;
        this.capacity = capacity;
        this.leftCapacity = capacity;
        this.teacher = teacher;
        this.students = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    private int getCapacity() {
        return capacity;
    }

    public int getLeftCapacity() {
        return this.leftCapacity;
    }

    public void setLeftCapacity(int capacity) {
        this.leftCapacity = capacity;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public boolean isFree() {
        return (leftCapacity > 0);
    }

    public Teacher getTeacher() {
        return this.teacher;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Project)) {
            return false;
        }
        Project that = (Project) obj;
        return (this.name.equals(that.getName()) && this.capacity == that.getCapacity());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this);
    }

    @Override
    public String toString() {
        return "Proiectul " + this.name + " are capacitatea maxima " + this.capacity;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }
}
