package com.main;

import java.util.Vector;

public class Student extends Person{

    private Vector<Project> projects = new Vector<>();

    public Student(String pName, String pEmail){
        super(pName, pEmail);
    }

    public void setPreferences(Vector<Project> projects){
        this.projects = projects;
    }
}
