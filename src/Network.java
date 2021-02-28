import java.util.Queue;
import java.util.PriorityQueue;

public class Network {
    // Each mission has its own network
    // Network availability can be checked before a message is to be sent
    // If a network is available then it can be used to transmit the full message

    // There are three types of deep space communications networks 
    // (2MB/sec with 80% availability, 2KB/sec with 90% availability and 20bits/sec with 99.9% availability).

    public Queue calculateBandwidth(){
        // todo random selection based on transmission width
        Queue<String> bandwidthQueue = new PriorityQueue<>(); 

        return bandwidthQueue;
    }

    public boolean checkNetwork() {
        boolean working;

        // TODO

        return working;
    }
}
