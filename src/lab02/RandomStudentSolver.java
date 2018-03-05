package lab02;

import java.util.List;

public class RandomStudentSolver extends Solver {
    public RandomStudentSolver(Problem problem) {
        super(problem);
    }

    @Override
    public void solve() {
        List<Student> students = problem.getStudents();
        for (Student student : students) {
            for (Project project : student.getPreferences()) {
                if (project.isFree() && project.getTeacher().isFree()) {
                    student.setAssignedProject(project);
                    project.setLeftCapacity(project.getLeftCapacity() - 1);
                    project.getTeacher().setLeftCapacity(project.getTeacher().getLeftCapacity() - 1);
                    break;
                }
            }
        }
    }

    @Override
    public String printResult() {
        return "Un cuplaj (neoptimal) este urmatorul: \n" +
                super.printResult();
    }
}
