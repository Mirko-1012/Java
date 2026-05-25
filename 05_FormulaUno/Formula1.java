public class Formula1 extends Object implements Runnable {
    private String autoName;
    private int lap;

    public Formula1(String autoName, int lap) {
        this.autoName = autoName;
        this.lap = lap;
    }
    
    @Override
    public void run() {
        System.out.println(autoName + " has started and has to do " + lap + " laps");

        for (int i = 1; i <= lap; i++)
            try {   
                Thread.sleep(500);
                System.out.println(autoName + " completed the lap  " + i + "/" + lap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println(autoName + " crossed the finish line!");
    }
}