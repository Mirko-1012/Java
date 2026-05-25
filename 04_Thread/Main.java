public class Main {
    public static void main(String[] args) {
        System.out.println("Ciao");
        SampleThread t = new SampleThread();
        t.start();
        System.out.println("Bye");
    }
}