package lab03;

public class Building extends Item implements Asset {
    @Override
    public float computeProfit() {
        if (getPrice() != 0) {
            return ((float)getValue() / (float)getPrice()) * financialRisk(getValue(), getPrice());
        } else {
            return 0;
        }
    }

    @Override
    public float getProfit(){
        return computeProfit();
    }

    public Building(String name, int value, int price) {
        super(name, value, price);
    }
}
