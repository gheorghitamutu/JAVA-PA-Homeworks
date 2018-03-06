/*
    Author: Mutu Gheorghita
*/

package lab03;

import java.util.List;

public class Portofolio {
    private List<Item> items;

    Portofolio(Algorithm algorithm, int maxValue, List<Item> items){
        this.items = algorithm.solve(items, maxValue);
    }

    @Override
    public String toString(){
        if(items.size() < 20) {
            return items.toString();
        }
        else{
            return "There are " + Integer.toString(items.size()) + " items!";
        }
    }
}
