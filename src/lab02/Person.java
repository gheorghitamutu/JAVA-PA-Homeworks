package com.main;

import java.util.Vector;

public class Person {
    private String name;
    private String email;
    private Vector<Project> projects = new Vector<>();

    Person(){

    }

    Person(String pName, String pEmail){
        setName(pName);
        setEmail(pEmail);
    }

    public void setName(String pName){
        name = pName;
    }

    public void setEmail(String pEmail){
        email = pEmail;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public void addProject(Project project){
        projects.add(project);
    }

}
