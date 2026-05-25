public class SampleThread extends Thread {
    
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("Sono nel Thread separato");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
