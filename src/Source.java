import Lab01.Compulsory;
import Lab01.Optional;

public class main {
    public static void main(String[] args){
        Compulsory comp = new Compulsory();
        comp.run();
        Optional opt = new Optional();
        opt.run(args);
    }
}
