/*
    Author: Mutu Gheorghita
*/

package lab03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Source {
    public static void example(){
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
    }
    public static void main(String[] args){
        example();

        /* Lab 03 Bonus*/
        ProblemGenerator pg = new ProblemGenerator();
        long startTime = System.nanoTime();
        pg.generateProblem(5000000, 10000000, 70, 50);
        pg.solveProblem(20000);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double seconds = (double) totalTime / 1000000000.0;
        System.out.println("Bonus part of Lab 03 took " + seconds + "!");
    }
}
