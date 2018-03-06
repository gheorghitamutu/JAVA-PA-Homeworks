/*
    Author: Mutu Gheorghita
*/

package lab03;

import java.util.ArrayList;
import java.util.List;

public class AssetManager {
    private List<Item> items = new ArrayList<>();

    public void add(List<Item> items){
        this.items.addAll(items);
    }

    public List<Item> getItems(){
        this.items.sort((item01, item02) -> item01.getName().compareToIgnoreCase(item02.getName()));
        return this.items;
    }

    public List<Item> getAssets(){
        List<Item> assets = new ArrayList<>();
        for(Item item: this.items){
            if(item.getValue() != 0){
                assets.add(item);
            }
        }

        // you need negative value in order to specify that condition is not met to lambda
        assets.sort((item01, item02) -> (((Asset)item01).computeProfit() > ((Asset)item02).computeProfit()) ? 1 : -1);

        return assets;
    }

    public Portofolio createPortofolio(Algorithm algorithm, int maxValue){
        return new Portofolio(algorithm, maxValue, items);
    }
}
