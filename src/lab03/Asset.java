package lab03;

public interface Asset {
    float computeProfit();
    default float financialRisk(int value, int price){
        float risk;
        if(value/price > 1){
            risk = 1 - ((float)price/(float)value);
        }
        else{
            risk = 1 - ((float)value/(float)price);
        }
        return risk;
    }
}
