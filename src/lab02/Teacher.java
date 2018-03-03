package com.main;

import java.util.Vector;

public class Teacher extends Person {

    private Vector<Student> students = new Vector<>();

    public Teacher(String pName, String pEmail){
        super(pName, pEmail);
    }

    public Project createProject(String name, int capacity){
        Project p = new Project(name, capacity);
        addProject(p);
        return p;
    }

    public void setPreferences(Vector<Student> students){
        this.students = students;
    }

}
