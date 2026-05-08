public class Main {
    public static void main(String[] args) {
        
        RemoteControlTV rctv = new RemoteControlTV();

        RemoteControlAir rcAir = new RemoteControlAir();

        SmartHome sm = new SmartHome();
        sm.add(rctv);
        sm.add(rcAir);
    }
}