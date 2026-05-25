public class Main {
    public static void main(String[] args) {

        Formula1 car1 = new Formula1("Ferrari", 3);
        Formula1 car2 = new Formula1("Mercedes", 5);
        Formula1 car3 = new Formula1("McLaren", 5);

        car1.start();
        car2.start();
        car3.start();
    }
}