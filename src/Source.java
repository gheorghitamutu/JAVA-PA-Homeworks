// Author: Mutu Gheorghita

import lab01.*;

public class Source {
    public static void main(String[] args){
        Compulsory comp = new Compulsory();
        comp.run();
        Optional opt = new Optional();
        opt.run(args);
        Advanced adv = new Advanced();
        adv.run(5);
    }
}
