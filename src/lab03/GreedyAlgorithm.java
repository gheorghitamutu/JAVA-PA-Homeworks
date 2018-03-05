package lab03;

import java.util.ArrayList;
import java.util.List;

public class GreedyAlgorithm implements Algorithm {
    @Override
    public List<Item> solve(List<Item> items, int maxValue){
        List<Item> maxItems = new ArrayList<>();
        float sum = 0;
        List<Item> reverseSortedItems = sortReverseItems(items);
        for(Item item: reverseSortedItems){
            sum += item.getProfit();
            if(sum < maxValue) {
                maxItems.add(item);
            }
            else{
                break;
            }
        }

        return maxItems;
    }

    private List<Item> sortReverseItems(List<Item> items){
        items.sort((item01, item02) -> Float.compare(item02.getProfit(), item01.getProfit()));
        return items;
    }
}
