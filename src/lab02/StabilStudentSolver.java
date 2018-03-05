package lab02;

import java.util.ArrayList;
import java.util.List;

public class StabilStudentSolver extends Solver {

    public StabilStudentSolver(Problem problem) {
        super(problem);
    }

    private void removeImpossibleAssignmentsFromStudents() {
        List<Student> students = problem.getStudents();
        for (Student student : students) {
            if (student.getPreferences() == null) {
                System.out.println(student.getName() + " null!");
            }
            student.getPreferences().removeIf(project -> !project.getTeacher().getStudents().contains(student));
        }
    }

    private void subscribeStudentToProject(Student student, Project project) {
        if (student == null || project == null) {
            System.out.println("Student or Project can't be null!");
            System.exit(0);
        }

        Teacher teacher = project.getTeacher();
        teacher.setLeftCapacity(teacher.getLeftCapacity() - 1);
        project.setLeftCapacity(project.getLeftCapacity() - 1);
        student.setAssignedProject(project);
        project.addStudent(student);
    }

    private void unsubscribeStudentFromProject(Student student, Project project) {
        if (student == null || project == null) {
            System.out.println("Student or Project can't be null!");
            System.exit(0);
        }

        Teacher teacher = project.getTeacher();
        teacher.setLeftCapacity(teacher.getLeftCapacity() + 1);
        project.setLeftCapacity(project.getLeftCapacity() + 1);
        project.getStudents().remove(student);
        student.getPreferences().remove(project);
        student.setAssignedProject(null);

    }

    private Student getLastStudentAssignedToProject(Project project) {
        Teacher teacher = project.getTeacher();
        List<Student> intersection = new ArrayList<>();
        for (Student student : teacher.getStudents()) {
            if (project.getStudents().contains(student)) {
                intersection.add(student);
            }
        }

        return intersection.get(intersection.size() - 1);
    }

    private Student getLeastPreferredStudentFromTeacher(Teacher teacher) {
        List<Student> intersection = new ArrayList<>();

        for (Student student : teacher.getStudents()) {
            if (teacher.getAssignedStudents().contains(student)) {
                intersection.add(student);
            }
        }
        return intersection.get(intersection.size() - 1);
    }

    @Override
    public void solve() {
        removeImpossibleAssignmentsFromStudents();
        boolean finished = false;
        while (!finished) {
            finished = true;
            for (Student student : problem.getStudents()) {
                if (student.isFree() && (student.getPreferences().size() > 0)) {
                    finished = false;
                    Project mostDesiredProject = student.getPreferences().get(0);
                    Teacher projectOwner = mostDesiredProject.getTeacher();
                    subscribeStudentToProject(student, mostDesiredProject);
                    if (mostDesiredProject.getLeftCapacity() < 0) {
                        Student worstStudentAssignedAtProject = getLastStudentAssignedToProject(mostDesiredProject);
                        unsubscribeStudentFromProject(worstStudentAssignedAtProject, mostDesiredProject);
                    } else if (projectOwner.getLeftCapacity() < 0) {
                        Student worstStudentAssignedToLecturer = getLeastPreferredStudentFromTeacher(projectOwner);
                        unsubscribeStudentFromProject(worstStudentAssignedToLecturer,
                                worstStudentAssignedToLecturer.getAssignedProject());
                    }
                }
            }
        }
    }


    @Override
    public String printResult() {
        return "Cuplajul stabil, optimal din perspectiva studentilor este urmatorul: \n" +
                super.printResult();
    }
}
