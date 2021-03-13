package network;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Network {

    static Random random = new Random();
    BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>();

    private String availability;
    private int bandwidth;
    private int latency = 1;

    public Network() {
        this.availability = checkNetworkAvailability();
        this.bandwidth = setbandwith(availability);
        // this.latency = setNetworkLatency(distanceTravelled);

    }

    // network speeds are all in bits
    public enum NetworkType {
        // 2MB at 80%
        MAIN(16_000_000),
        // 2KB at 90%
        SECONDARY(16_000),
        // 20 bits at 99%
        BACKUP(20);

        private final int bandwidth;

        private NetworkType(int bandwidth){
            this.bandwidth = bandwidth;
        }

        private int showBandwidth(){
            return this.bandwidth;
        }
    }

    // There are three types of deep space communications networks 
    private static String checkNetworkAvailability(){
        //80% availabilty, so 20 % chance of failure
        if ((random.nextInt(10) + 1) > 2) {
            return("MAIN");
        }
        // 90% availability, so 10% chance of failure
        else if((random.nextInt(10) + 1) > 1){
            return("SECONDARY"); 
        }
        // 99.9% availability, so 0.01 percent chance of failure
        else{
            return("BACKUP");
        }
    }

    private static int simulateMissionDistance() {
        // Hard code mission destination distance for the moment
        final int distance = 100;
        // TODO
        return distance;
    } 

    private static int setbandwith(String availability) {
        NetworkType network = NetworkType.valueOf(availability);
        return network.showBandwidth();
    }


    // subject to increasing delays as the mission travels further away from Earth
    private int setNetworkLatency(int distanceTravelled){
        // adds 1 second of latency per 1000 kilometers travelled
        return this.latency;
    }

    public String getAvailability(){
        return this.availability; 
    }

    public int getBandwidth(){
        return this.bandwidth; 
    }

    public int getLatency(){
        return this.latency; 
    }
}
