package primaryClasses;

public class Network implements Runnable{
    
    // network speeds are all in bits
    enum NetworkType {
        // 2MB
        MAIN(16000000, 0.8),
        // 2KB
        SECONDARY(16000, 0.9),
        // 20 bits
        BACKUP(20, 0.999);

        private int bandwidth;
        private double availability;

        private NetworkType(int bandwidth, double availability){
            this.bandwidth = bandwidth;
            this.availability = availability;
        }
    }

    public void run(){
        //TODO
    }

}
