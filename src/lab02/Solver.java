/*
    Author: Mutu Gheorghita
*/

package lab02;

public abstract class Solver {
    Problem problem;

    Solver(Problem problem) {
        this.problem = problem;
    }

    public abstract void solve();

    public String printResult() {
        StringBuilder result = new StringBuilder();
        for (Student student : problem.getStudents()) {
            result.append("Studentul ").append(student.getName());
            if (student.getAssignedProject() != null) {
                result.append(" are asignat proiectul ").append(student.getAssignedProject().getName()).append(".\n");
            } else {
                result.append(" nu are asignat niciun proiect.\n");
            }
        }
        return result.toString();
    }
}
