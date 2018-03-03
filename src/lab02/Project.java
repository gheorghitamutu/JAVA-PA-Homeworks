package com.main;

import java.util.Vector;

public class Project {
    private String name;
    private int capacity;

    public Project(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
    }

    public void setName(String pName){
        name = pName;
    }

    public void setCapacity(int pCapacity){
        capacity = pCapacity;
    }

    public String getName(){
        return name;
    }

    public int getCapacity(){
        return capacity;
    }


}
