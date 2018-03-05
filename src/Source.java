/*
    Author: Mutu Gheorghita
*/

import lab02.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Source {

    private static Problem example() {
        Problem problem = new Problem();

        Student s1 = new Student("S1", "s1@info.uaic.ro");
        Student s2 = new Student("S2", "s2@info.uaic.ro");
        Student s3 = new Student("S3", "s3@info.uaic.ro");
        Student s4 = new Student("S4", "s4@info.uaic.ro");

        Teacher t1 = new Teacher("T1", "t1@info.uaic.ro", 3);
        Teacher t2 = new Teacher("T2", "t2@info.uaic.ro", 2);
        Teacher t3 = new Teacher("T3", "t3@info.uaic.ro", 2);

        Project p1 = t1.createProject("P1", 2);
        Project p2 = t2.createProject("P2", 1);
        Project p3 = t3.createProject("P3", 1);

        t1.setPreferences(new ArrayList<>(Arrays.asList(s3, s1, s2, s4)));
        t2.setPreferences(new ArrayList<>(Arrays.asList(s1, s2, s3, s4)));
        t3.setPreferences(new ArrayList<>(Arrays.asList(s4, s3, s1, s2)));

        s1.setPreferences(new ArrayList<>(Arrays.asList(p1, p2, p3)));
        s2.setPreferences(new ArrayList<>(Arrays.asList(p1, p3, p2)));
        s3.setPreferences(new ArrayList<>(Collections.singletonList(p1)));
        s4.setPreferences(new ArrayList<>(Arrays.asList(p3, p2, p1)));

        problem.setStudents(new ArrayList<>(Arrays.asList(s1, s2, s3, s4)));
        problem.setTeachers(new ArrayList<>(Arrays.asList(t1, t2, t3)));
        System.out.println(problem);


        return problem;
    }

    public static void main(String[] args) {
        /* Lab 01
        Compulsory comp = new Compulsory();
        comp.run();
        Optional opt = new Optional();
        opt.run(args);
        Advanced adv = new Advanced();
        adv.run(5);
        */

        Problem problem = example();
        Solver optimalSolver = new StabilStudentSolver(problem);
        optimalSolver.solve();
        System.out.println(optimalSolver.printResult());
        problem = example();
        Solver randomSolver = new RandomStudentSolver(problem);
        randomSolver.solve();
        System.out.println(randomSolver.printResult());

    }
}
