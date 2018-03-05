package lab03;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProblemGenerator {
    private List<Item> items = new ArrayList<>();

    public ProblemGenerator() {

    }

    public void generateProblem(int maxAssets, int maxItems, int maxValue, int maxPrice) {
        if (maxItems < maxAssets) {
            System.out.println("Items are assets + other items. MaxItems can't be lower than maxAssets!");
            System.exit(0);
        }

        if(maxValue < maxPrice){
            System.out.println("What's the point in buying something with a higher price than you sell it with?!");
            System.exit(0);
        }

        int countAssets = 0;
        int countItems = maxAssets;

        while (countAssets < maxAssets) {
            int generatedValue = 0;
            int generatedPrice = generateInt(0, maxPrice);

            // make sure you don't buy stupid stuff
            while(generatedValue < generatedPrice){
                generatedValue = generateInt(0, maxValue);
            }
            if (countAssets % 2 == 0) {
                items.add(new Building(("House " + Integer.toString(countAssets)), generatedValue, generatedPrice));
            } else {
                items.add(new Vehicle(("Car " + Integer.toString(countAssets)), generatedValue, generatedPrice));
            }
            countAssets++;
        }

        while (countItems < maxItems) {
            items.add(new Jewel(("Ring " + Integer.toString(countItems)), generateInt(0, maxPrice)));
            countItems++;
        }
    }

    public void solveProblem(int maxPortofolioValue) {
        if (maxPortofolioValue < 1) {
            System.out.println("You can't have a potofolio with less than 1 value!");
            System.exit(0);
        }

        AssetManager manager = new AssetManager();
        manager.add(items);

        if (items.size() < 20) {
            System.out.println("Items sorted by the name: " + manager.getItems());
            System.out.println("Assets sorted in ascending order by the profit: " + manager.getAssets());
        } else {
            System.out.println("More than 20 items to print!");
        }

        Portofolio solution = manager.createPortofolio(new GreedyAlgorithm(), maxPortofolioValue);
        System.out.println("The best greedy portofolio in descending order: " + solution);

        solution = manager.createPortofolio(new RandomAlgorithm(), maxPortofolioValue);
        System.out.println("The best random portofolio in ascending order: " + solution);
    }

    private int generateInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
