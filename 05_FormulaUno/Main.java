import java.util.Random;

public class Main {
    public static void main(String[] args) {
        
        Random random = new Random();

        int FerrariLaps = random.nextInt(5) + 1;
        int RedBullLaps = random.nextInt(5) + 1;
        int McLarenLaps = random.nextInt(5) + 1;

        Formula1 car1 = new Formula1("Ferrari", FerrariLaps);
        Formula1 car2 = new Formula1("RedBull", RedBullLaps);
        Formula1 car3 = new Formula1("McLaren", McLarenLaps);

        Thread t1 = new Thread(car1);
        Thread t2 = new Thread(car2);
        Thread t3 = new Thread(car3);

        t1.start();
        t2.start();
        t3.start();
    }
}