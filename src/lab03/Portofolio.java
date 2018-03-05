package lab03;

import java.util.List;

public class Portofolio {
    private List<Item> items;

    Portofolio(Algorithm algorithm, int maxValue, List<Item> items){
        this.items = algorithm.solve(items, maxValue);
    }

    @Override
    public String toString(){
        return items.toString();
    }
}
