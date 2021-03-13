public class Network implements Runnable{
    
    // network speeds are all in bits
    enum NetworkType {
        // 2MB at 80%
        MAIN(16_000_000, 0.8),
        // 2KB at 90%
        SECONDARY(16_000, 0.9),
        // 20 bits at 99%
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
