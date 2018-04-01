/*
    Author: Mutu Gheorghita
*/

package lab03;

public class Item {
    private int value;
    private int price;
    private String name;

    Item(String name, int value, int price){
        this.name = name;
        this.value = value;
        this.price = price;
    }

    Item(String name, int price){
        this.name = name;
        this.price = price;
        this.value = 0;
    }

    int getValue(){
        return this.value;
    }

    int getPrice() {
        return this.price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name + " with profit " + Float.toString(getProfit());
    }

    public float getProfit(){
        return (float)getPrice();
    }

}
