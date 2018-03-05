/*
    Author: Mutu Gheorghita
*/

import lab03.ProblemGenerator;

public class Source {

    /* Lab 02
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
    */

    public static void main(String[] args) {
        /* Lab 01
        Compulsory comp = new Compulsory();
        comp.run();
        Optional opt = new Optional();
        opt.run(args);
        Advanced adv = new Advanced();
        adv.run(5);
        */

        /* Lab 02
        Problem problem = example();
        Solver optimalSolver = new StabilStudentSolver(problem);
        optimalSolver.solve();
        System.out.println(optimalSolver.printResult());
        problem = example();
        Solver randomSolver = new RandomStudentSolver(problem);
        randomSolver.solve();
        System.out.println(randomSolver.printResult());
        */

        /* Lab 03
        Building h1 = new Building("House 1", 27, 9);
        Building h2 = new Building("House 2", 36, 9);
        Building h3 = new Building("House 3", 45, 9);

        Vehicle c1 = new Vehicle("Car 1", 8, 4);
        Vehicle c2 = new Vehicle("Car 2", 12, 4);

        Jewel ring = new Jewel("Ring", 2);

        AssetManager manager = new AssetManager();
        manager.add(new ArrayList<>(Arrays.asList(h1, h2, h3)));
        manager.add(new ArrayList<>(Arrays.asList(c1, c2)));
        manager.add(new ArrayList<>(Collections.singletonList(ring)));

        System.out.println("Items sorted by the name: " + manager.getItems());
        System.out.println("Assets sorted in ascending order by the profit: " + manager.getAssets());

        int maxValue = 10;
        Portofolio solution = manager.createPortofolio(new GreedyAlgorithm(), maxValue);
        System.out.println("The best greedy portofolio in descending order: " + solution);

        solution = manager.createPortofolio(new RandomAlgorithm(), maxValue);
        System.out.println("The best random portofolio in ascending order: " + solution);
        */

        ProblemGenerator pg = new ProblemGenerator();
        pg.generateProblem(5000, 10000, 6, 5);
        pg.solveProblem(2000);
    }
}
