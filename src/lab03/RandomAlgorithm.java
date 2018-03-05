package lab03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomAlgorithm implements Algorithm {
    @Override
    public List<Item> solve(List<Item> items, int maxValue) {
        Collections.shuffle(items);
        List<Item> maxItems = new ArrayList<>();
        float sum = 0;

        for (Item item : items) {
            sum += item.getProfit();
            if (sum <= maxValue) {
                maxItems.add(item);
            } else {
                sum -= item.getProfit();
            }
        }

        maxItems.sort((item01, item02) -> Float.compare(item01.getProfit(), item02.getProfit()));
        return maxItems;
    }
}
