package Lab01;

public class Compulsory {
    private String[] languages = new String[]{
            "C", "C++", "C#", "Go", "JavaScript", "Perl", "PHP", "Python", "Swift", "Java"};
    private int result = (int) (Math.random() * 1_000_000);

    private static int sumDigits(int number) {
        int sum = 0;

        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }

        return (sum < 10) ? sum : sumDigits(sum);
    }

    public void run() {
        this.result *= 3;
        this.result += 0b10101;
        this.result += 0xFF;
        this.result *= 6;

        int sum = sumDigits(this.result);
        System.out.println("Willy-nilly, this semester I will learn " + this.languages[sum] + "!");
    }
}
