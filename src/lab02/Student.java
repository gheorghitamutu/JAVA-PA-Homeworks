/*
    Author: Mutu Gheorghita
*/

package lab02;

import java.util.List;

public class Student extends Person {

    private List<Project> projects;
    private Project assignedProject;

    public Student(String name, String email) {
        super(name, email);
    }

    public void setPreferences(List<Project> projects) {
        this.projects = projects;
    }

    public String toString() {
        StringBuilder preferences = new StringBuilder();
        preferences.append(this.getName());
        preferences.append(": (");
        for (int index = 0; index < projects.size(); index++) {
            preferences.append(projects.get(index).getName());
            if (index != projects.size() - 1) {
                preferences.append(", ");
            } else {
                preferences.append(")");
            }
        }
        return preferences.toString();
    }

    public Project getAssignedProject() {
        return this.assignedProject;
    }

    public void setAssignedProject(Project project) {
        this.assignedProject = project;
    }

    public List<Project> getPreferences() {
        return this.projects;
    }

    @Override
    public boolean isFree() {
        return (assignedProject == null);
    }
}
